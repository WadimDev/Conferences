package ua.springboot.training.group6.conferences.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.springboot.training.group6.conferences.adaptors.persistence.ConferenceDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.TalkDao;
import ua.springboot.training.group6.conferences.adaptors.persistence.TalkTypeDao;

@RunWith(MockitoJUnitRunner.class)
class ConferenceServiceTest {
    @Mock
    private ConferenceDao conferenceDao;
    @Mock
    private TalkTypeDao talkTypeDao;
    @Mock
    private TalkDao talkDao;

    private ConferenceService conferenceService;

    @BeforeEach
    void init() {
        conferenceService = new ConferenceServiceImpl(conferenceDao, talkDao, talkTypeDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifAuthorIsEmptyThrowException() {
//        conferenceService.createConference()
//        conferenceService.getConferences()
//        conferenceService.modifyConference()
//        conferenceService.createTalkForConference()
//        conferenceService.getTalksForConference()
    }

    @Test
    public void ifNoConferencesPassedEmptyListIsReturned() {
//        assertThat(conferenceService.).isEmpty();
    }
}