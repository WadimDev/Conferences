package ua.springboot.training.group6.conferences.adaptors.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

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
