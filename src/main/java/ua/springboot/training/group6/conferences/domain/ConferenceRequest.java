package ua.springboot.training.group6.conferences.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

// названием    обязательные     не пустым    уникальны
// тематикой   обязательные  не пустым
// датами проведения   обязательные  не пустым  не должны пересекаться(400 HTTP )
// количеством участников   обязательные     не пустым     > 100
@Data
public class ConferenceRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String theme;

    @NotNull
    private Date date;

    @Min(101)
    private int participantsCount;
}
