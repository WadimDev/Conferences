package ua.springboot.training.group6.conferences.adaptors.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;
import ua.springboot.training.group6.conferences.service.ConferenceService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/conferences")
@RequiredArgsConstructor
public class ConferencesRestController {
    private final ConferenceService conferenceService;

    //-	добавление новой конференции (POST на /conferences) с названием, тематикой, датами проведения и количеством участников;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addConference(@RequestBody @Valid ConferenceRequest conferenceRequest) {
        LOG.info("ConferencesRestController > addConference");

        conferenceService.createConference(conferenceRequest);

        LOG.info("conferenceRequest: " + conferenceRequest);

//конференции уникальны по имени, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	все поля у конференции и доклада обязательные и должны быть не пустыми, количество участников > 100,
//            -	при нарушении этих правил должен возвращаться 400 HTTP статус;
//-	даты конференций не должны пересекаться, иначе возвращаться 400 HTTP статус;
    }

    //-	получение списка всех конференций (GET на /conferences);
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ConferenceResponse> getConferences() {
        LOG.info("ConferencesRestController > getConferences");

        return conferenceService.getConferences();
    }


    //-	изменение информации о конференции (PUT на /conferences/{conference_id});
    @PutMapping("/{conference_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeConference(@PathVariable("conference_id") String conferenceId,
                                 @RequestBody @Valid ConferenceRequest conferenceRequest) {
        LOG.info("ConferencesRestController > changeConference");
        LOG.info("conferenceId: " + conferenceId);
        LOG.info("conferenceRequest: " + conferenceRequest);

        conferenceService.modifyConference(conferenceId, conferenceRequest);

//response and if an existing resource is modified, either the 200 (OK) or 204 (No Content)
// response codes SHOULD be sent to indicate successful completion of the request.
    }

    //-	добавление доклада в конференцию (POST на /conferences/{conference_id}/talks) с
    //    названием, описанием, именем докладчика и типом доклада (доклад, мастер-класс, воркшоп);
    @PostMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTalk(@PathVariable("conference_id") String conferenceId, @RequestBody @Valid TalkRequest talkRequest) {
        LOG.info("ConferencesRestController > addTalk");

        conferenceService.createTalkForConference(conferenceId, talkRequest);

//-	докладчик не может подать больше 3 докладов, при попытке подать больше возвращается 400 HTTP статус;
//-	доклады уникальны по названию, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	подача докладов разрешена не позже чем за месяц до начала конференции, иначе возвращается 400 HTTP статус.

    }

    //-	получение списка поданных докладов на конференцию (GET на /conferences/{conference_id}/talks);
    @GetMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.OK)
    public List<TalkResponse> getTalksForConference(@PathVariable("conference_id") String conferenceId) {
        LOG.info("ConferencesRestController > getTalksForConference");

        return conferenceService.getTalksForConference(conferenceId);
    }


//-	конференции уникальны по имени, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	все поля у конференции и доклада обязательные и должны быть не пустыми, количество участников > 100,
// 	при нарушении этих правил должен возвращаться 400 HTTP статус;
//-	даты конференций не должны пересекаться, иначе возвращаться 400 HTTP статус;
//-	докладчик не может подать больше 3 докладов, при попытке подать больше возвращается 400 HTTP статус;
//-	доклады уникальны по названию, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	подача докладов разрешена не позже чем за месяц до начала конференции, иначе возвращается 400 HTTP статус.


    @ExceptionHandler(Exception.class)//FIXME
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    void onSaveError(Exception exception) {
        LOG.error("Exception", exception);
    }

    @ExceptionHandler(SQLException.class)//FIXME
    @ResponseStatus(value = HttpStatus.CONFLICT)
    void onSaveError409(SQLException exception) {
        LOG.error("SQLException", exception);
    }
}
