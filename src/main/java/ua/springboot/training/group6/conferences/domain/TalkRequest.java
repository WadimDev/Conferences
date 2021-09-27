package ua.springboot.training.group6.conferences.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

// названием    уникальны   обязательные    не пустыми
// описанием
// именем докладчика   обязательные    не пустыми        не может подать больше 3 докладов
// типом доклада (доклад, мастер-класс, воркшоп)   обязательные    не пустыми
// подача докладов разрешена не позже чем за месяц до начала конференции
@Data
public class TalkRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String rapporteurName;

    @NotBlank
    private String talkType;
}
