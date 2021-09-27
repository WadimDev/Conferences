package ua.springboot.training.group6.conferences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.springboot.training.group6.conferences.adaptors.persistence.ConferenceDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Conference;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Talk;
import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao;

    @Override
    public void createConference(ConferenceRequest conferenceRequest) {
        conferenceDao.save(Conference.of(conferenceRequest));
    }

    @Override
    public List<ConferenceResponse> getConferences() {
        return conferenceDao.findAll().stream().
                map(conference -> conference.toConferenceResponse()).
                collect(Collectors.toList());//FIXME
    }

    @Override
    public void modifyConference(String conferenceId, ConferenceRequest conferenceRequest) {
        var conferenceInDb = conferenceDao.findById(Long.valueOf(conferenceId)).
                orElseThrow(() -> new RuntimeException("Not found"));//FIXME

        var newConference = Conference.of(conferenceRequest);
        newConference.setId(conferenceInDb.getId());

        conferenceDao.save(newConference);
    }

    @Override
    public void createTalkForConference(String conferenceId, TalkRequest talkRequest) {
        var conferenceInDb = conferenceDao.findById(Long.valueOf(conferenceId)).
                orElseThrow(() -> new RuntimeException("Not found"));

        conferenceInDb.addTalk(Talk.of(talkRequest));

        conferenceDao.save(conferenceInDb);
//FIXME
    }

    @Override
    public List<TalkResponse> getTalksForConference(String conferenceId) {
        var conferenceInDb = conferenceDao.findById(Long.valueOf(conferenceId)).
                orElseThrow(() -> new RuntimeException("Not found"));

        return conferenceInDb.getTalks().stream().
                map(talk -> talk.toTalkResponse()).
                collect(Collectors.toList());//FIXME
    }
}
