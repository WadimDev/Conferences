package ua.springboot.training.group6.conferences.adaptors.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Talk;

import java.util.Optional;

public interface TalkDao extends JpaRepository<Talk, Long> {
    Optional<Talk> findByTitle(String title);

    Long countByRapporteurNameAndConferenceId(String rapporteurName, Long conferenceId);
}
