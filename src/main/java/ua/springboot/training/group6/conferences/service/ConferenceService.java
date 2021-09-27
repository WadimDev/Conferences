package ua.springboot.training.group6.conferences.service;

import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;

import java.util.List;

public interface ConferenceService {
    void createConference(ConferenceRequest conferenceRequest);

    List<ConferenceResponse> getConferences();

    void modifyConference(String conferenceId, ConferenceRequest conferenceRequest);

    void createTalkForConference(String conferenceId, TalkRequest talkRequest);

    List<TalkResponse> getTalksForConference(String conferenceId);
}
