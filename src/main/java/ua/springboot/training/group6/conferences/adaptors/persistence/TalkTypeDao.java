package ua.springboot.training.group6.conferences.adaptors.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.TalkType;

public interface TalkTypeDao extends JpaRepository<TalkType, Long> {
    TalkType findByType(String type);
}
