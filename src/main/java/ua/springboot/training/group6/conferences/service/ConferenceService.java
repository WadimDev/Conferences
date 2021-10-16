package ua.springboot.training.group6.conferences.service;

import ua.springboot.training.group6.conferences.adaptors.api.dto.ConferenceRequest;
import ua.springboot.training.group6.conferences.adaptors.api.dto.ConferenceResponse;
import ua.springboot.training.group6.conferences.adaptors.api.dto.TalkRequest;
import ua.springboot.training.group6.conferences.adaptors.api.dto.TalkResponse;

import java.util.List;

public interface ConferenceService {
    ConferenceResponse createConference(ConferenceRequest conferenceRequest);

    List<ConferenceResponse> getConferences();

    ConferenceResponse modifyConference(long conferenceId, ConferenceRequest conferenceRequest);

    TalkResponse createTalkForConference(long conferenceId, TalkRequest talkRequest);

    List<TalkResponse> getTalksForConference(long conferenceId);
}
