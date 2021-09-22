package ua.springboot.training.group6.conferences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.springboot.training.group6.conferences.adaptors.persistence.ConferenceDao;
import ua.springboot.training.group6.conferences.domain.Conference;
import ua.springboot.training.group6.conferences.domain.Talk;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao;

    @Override
    public void createConference(Conference conference) {
        conferenceDao.save(conference);
    }

    @Override
    public List<Conference> getConferences() {
        return conferenceDao.findAll();
    }

    @Override
    public void modifyConference(String conferenceId, Conference conference) {
        conferenceDao.findById(Long.valueOf(conferenceId)).orElseThrow();

//        @Modifying
//        @Query("update User u set u.firstname = ?1, u.lastname = ?2 where u.id = ?3")
//        void setUserInfoById(String firstname, String lastname, Integer userId);
    }

    @Override
    public void createTalkForConference(String conferenceId, Talk talk) {

    }

    @Override
    public List<Talk> getTalsForConference(String conferenceId) {
        return null;
    }
}
