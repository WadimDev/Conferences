package ua.springboot.training.group6.conferences.adaptors.api.dto;

import lombok.Data;

@Data
public class ConferenceResponse extends ConferenceRequest {
    private Long id;
}
