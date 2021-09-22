package ua.springboot.training.group6.conferences.service;

import ua.springboot.training.group6.conferences.domain.Conference;
import ua.springboot.training.group6.conferences.domain.Talk;

import java.util.List;

public interface ConferenceService {
    void createConference(Conference conference);

    List<Conference> getConferences();

    void modifyConference(String conferenceId, Conference conference);

    void createTalkForConference(String conferenceId, Talk talk);

    List<Talk> getTalsForConference(String conferenceId);
}
