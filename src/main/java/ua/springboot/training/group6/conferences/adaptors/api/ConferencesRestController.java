package ua.springboot.training.group6.conferences.adaptors.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.springboot.training.group6.conferences.adaptors.api.dto.ConferenceRequest;
import ua.springboot.training.group6.conferences.adaptors.api.dto.ConferenceResponse;
import ua.springboot.training.group6.conferences.adaptors.api.dto.TalkRequest;
import ua.springboot.training.group6.conferences.adaptors.api.dto.TalkResponse;
import ua.springboot.training.group6.conferences.exception.DuplicateException;
import ua.springboot.training.group6.conferences.service.ConferenceService;

import javax.validation.Valid;
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
    public ConferenceResponse addConference(@RequestBody @Valid ConferenceRequest conferenceRequest) {
        LOG.info("ConferencesRestController > addConference");

        return conferenceService.createConference(conferenceRequest);
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
    @ResponseStatus(HttpStatus.OK)
    public ConferenceResponse changeConference(@PathVariable("conference_id") long conferenceId,
                                               @RequestBody @Valid ConferenceRequest conferenceRequest) {
        LOG.info("ConferencesRestController > changeConference");
        LOG.info("conferenceId: " + conferenceId);
        LOG.info("conferenceRequest: " + conferenceRequest);

        return conferenceService.modifyConference(conferenceId, conferenceRequest);
//response and if an existing resource is modified, either the 200 (OK) or 204 (No Content)
// response codes SHOULD be sent to indicate successful completion of the request.
    }

    //-	добавление доклада в конференцию (POST на /conferences/{conference_id}/talks) с
    //    названием, описанием, именем докладчика и типом доклада (доклад, мастер-класс, воркшоп);
    @PostMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.CREATED)
    public TalkResponse addTalk(@PathVariable("conference_id") long conferenceId, @RequestBody @Valid TalkRequest talkRequest) {
        LOG.info("ConferencesRestController > addTalk");
        LOG.info("talkRequest: {}", talkRequest);

        return conferenceService.createTalkForConference(conferenceId, talkRequest);
//-	докладчик не может подать больше 3 докладов, при попытке подать больше возвращается 400 HTTP статус;
//-	доклады уникальны по названию, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	подача докладов разрешена не позже чем за месяц до начала конференции, иначе возвращается 400 HTTP статус.
    }

    //-	получение списка поданных докладов на конференцию (GET на /conferences/{conference_id}/talks);
    @GetMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.OK)
    public List<TalkResponse> getTalksForConference(@PathVariable("conference_id") long conferenceId) {
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


//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    void onSaveError(NotFoundException exception) {
//        LOG.error("NotFoundException", exception);
//    }
//
//    @ExceptionHandler(CountTalksException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    void onSaveError(CountTalksException exception) {
//        LOG.error("NotFoundException", exception);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    void onSaveError(Exception exception) {
        LOG.error("Exception", exception);
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    void onDuplicateException(DuplicateException exception) {
        LOG.error("DuplicateException", exception);
    }
}
