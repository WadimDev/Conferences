package ua.springboot.training.group6.conferences.adaptors.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.springboot.training.group6.conferences.domain.Conference;
import ua.springboot.training.group6.conferences.domain.Talk;
import ua.springboot.training.group6.conferences.service.ConferenceService;

import javax.validation.Valid;
import java.util.List;

@RestController("/conferences")
@RequiredArgsConstructor
public class ConferencesRestController {
    private final ConferenceService conferenceService;

    //-	добавление новой конференции (POST на /conferences) с названием, тематикой, датами проведения и количеством участников;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addConference(@RequestBody @Valid Conference conference) {
        conferenceService.createConference(conference);

//конференции уникальны по имени, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	все поля у конференции и доклада обязательные и должны быть не пустыми, количество участников > 100,
//            -	при нарушении этих правил должен возвращаться 400 HTTP статус;
//-	даты конференций не должны пересекаться, иначе возвращаться 400 HTTP статус;
    }

    //-	получение списка всех конференций (GET на /conferences);
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Conference> getConferences() {
        return conferenceService.getConferences();
    }


    //-	изменение информации о конференции (PUT на /conferences/{conference_id});
    @PutMapping("/{conference_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeConference(@PathVariable("conference_id") String conferenceId,
                                 @RequestBody @Valid Conference conference) {
        conferenceService.modifyConference(conferenceId, conference);

//response and if an existing resource is modified, either the 200 (OK) or 204 (No Content)
// response codes SHOULD be sent to indicate successful completion of the request.
    }

    //-	добавление доклада в конференцию (POST на /conferences/{conference_id}/talks) с
    //    названием, описанием, именем докладчика и типом доклада (доклад, мастер-класс, воркшоп);
    @PostMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTalk(@PathVariable("conference_id") String conferenceId, @RequestBody @Valid Talk talk) {
        conferenceService.createTalkForConference(conferenceId, talk);

//-	докладчик не может подать больше 3 докладов, при попытке подать больше возвращается 400 HTTP статус;
//-	доклады уникальны по названию, при попытке добавить дубликат должен возвращаться 409 HTTP статус;
//-	подача докладов разрешена не позже чем за месяц до начала конференции, иначе возвращается 400 HTTP статус.

    }

    //-	получение списка поданных докладов на конференцию (GET на /conferences/{conference_id}/talks);
    @GetMapping("/{conference_id}/talks")
    @ResponseStatus(HttpStatus.OK)
    public List<Talk> getTalksForConference(@PathVariable("conference_id") String conferenceId) {
        return conferenceService.getTalsForConference(conferenceId);
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
    void onSaveError() {
    }

    @ExceptionHandler(Exception.class)//FIXME
    @ResponseStatus(value = HttpStatus.CONFLICT)
    void onSaveError409() {
    }
}
