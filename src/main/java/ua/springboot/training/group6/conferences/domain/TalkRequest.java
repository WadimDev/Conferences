package ua.springboot.training.group6.conferences.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
