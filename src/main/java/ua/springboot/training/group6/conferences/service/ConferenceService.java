package ua.springboot.training.group6.conferences.service;

import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;

import java.util.List;

public interface ConferenceService {
    ConferenceResponse createConference(ConferenceRequest conferenceRequest);

    List<ConferenceResponse> getConferences();

    ConferenceResponse modifyConference(long conferenceId, ConferenceRequest conferenceRequest);

    TalkResponse createTalkForConference(long conferenceId, TalkRequest talkRequest);

    List<TalkResponse> getTalksForConference(long conferenceId);
}
