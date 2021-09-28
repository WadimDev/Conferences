package ua.springboot.training.group6.conferences.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.springboot.training.group6.conferences.adaptors.persistence.ConferenceDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.TalkDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.TalkTypeDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Conference;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Talk;
import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;
import ua.springboot.training.group6.conferences.exception.CountTalksException;
import ua.springboot.training.group6.conferences.exception.DateException;
import ua.springboot.training.group6.conferences.exception.DuplicateException;
import ua.springboot.training.group6.conferences.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private static final int COUNT_MONTH_BEFORE = 3;
    private static final int COUNT_RAPPORTEUR_TALKS_FOR_CONFERENCE = 3;

    private final ConferenceDao conferenceDao;
    private final TalkTypeDao talkTypeDao;
    private final TalkDao talkDao;

    @Override
    public ConferenceResponse createConference(ConferenceRequest conferenceRequest) {
        checkConferenceTitle(conferenceRequest);

        return conferenceDao.save(Conference.of(conferenceRequest)).toConferenceResponse();
    }

    @Override
    public List<ConferenceResponse> getConferences() {
        return conferenceDao.findAll().stream().
                map(Conference::toConferenceResponse).
                collect(Collectors.toList());
    }

    @Override
    public ConferenceResponse modifyConference(long conferenceId, ConferenceRequest conferenceRequest) {
        checkConferenceTitle(conferenceRequest, conferenceId);

        var conferenceInDb = conferenceDao.findById(conferenceId).
                orElseThrow(() -> new RuntimeException("Not found"));//FIXME

        var newConference = Conference.of(conferenceRequest);
        newConference.setId(conferenceInDb.getId());

        return conferenceDao.save(newConference).toConferenceResponse();
    }

    @Override
    public TalkResponse createTalkForConference(long conferenceId, TalkRequest talkRequest) {
        checkTalkTitle(talkRequest);
        checkRapporteurTalkCountForConference(talkRequest, conferenceId);

        var conferenceInDb = conferenceDao.findById(conferenceId).
                orElseThrow(() -> new RuntimeException("Not found"));

        checkConferenceDate(conferenceInDb);

        int talksSize = conferenceInDb.getTalks().size();

        var talkType = talkTypeDao.findByType(talkRequest.getTalkType());
        var talk = Talk.of(talkRequest);

        talk.setTalkType(talkType);
        conferenceInDb.addTalk(talk);

        return conferenceDao.save(conferenceInDb).getTalks().
                stream().skip(talksSize).findFirst().
                orElseThrow(() -> new RuntimeException("Unexpected exception")).//TODO refactor
                        toTalkResponse();
    }

    @Override
    public List<TalkResponse> getTalksForConference(long conferenceId) {
        var conferenceInDb = conferenceDao.findById(conferenceId).
                orElseThrow(() -> new NotFoundException("Conference not found"));

        return conferenceInDb.getTalks().stream().
                map(Talk::toTalkResponse).
                collect(Collectors.toList());
    }

    private void checkRapporteurTalkCountForConference(TalkRequest talkRequest, Long conferenceId) {
        var countRapporteurTalksForConference = talkDao.
                countByRapporteurNameAndConferenceId(talkRequest.getRapporteurName(), conferenceId);

        LOG.info("countRapporteurTalksForConference: {}", countRapporteurTalksForConference);

        if (countRapporteurTalksForConference >= COUNT_RAPPORTEUR_TALKS_FOR_CONFERENCE) {
            throw new CountTalksException("Count rapporteur talks for conferenceDate must be no more then tree.");
        }
    }

    private void checkConferenceTitle(ConferenceRequest conferenceRequest) {
        conferenceDao.findByTitle(conferenceRequest.getTitle()).
                ifPresent(conference -> {
                    throw new DuplicateException("Conference with such title already exist.");
                });
    }

    private void checkConferenceTitle(ConferenceRequest conferenceRequest, Long conferenceId) {
        conferenceDao.findByTitleAndIdNot(conferenceRequest.getTitle(), conferenceId).
                ifPresent(conference -> {
                    throw new DuplicateException("Conference with such title already exist.");
                });
    }

    private void checkConferenceDate(Conference conferenceInDb) {
        var dateNow = LocalDate.now();
        var dateTreeMonthBefore = conferenceInDb.getDate().toLocalDate().minusMonths(COUNT_MONTH_BEFORE);

        if (dateTreeMonthBefore.isBefore(dateNow)) {
            throw new DateException("Date must be earlier then tree mouth.");
        }
    }

    private void checkTalkTitle(TalkRequest talkRequest) {
        talkDao.findByTitle(talkRequest.getTitle()).
                ifPresent(conference -> {
                    throw new DuplicateException("Talk with such title already exist.");
                });
    }

}
