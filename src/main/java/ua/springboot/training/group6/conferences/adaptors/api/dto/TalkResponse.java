package ua.springboot.training.group6.conferences.adaptors.api.dto;

import lombok.Data;

@Data
public class TalkResponse extends TalkRequest {
    private Long id;
}
