package ua.springboot.training.group6.conferences.adaptors.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.springboot.training.group6.conferences.adaptors.persistence.entity.Conference;

import java.util.Optional;

public interface ConferenceDao extends JpaRepository<Conference, Long> {
    Optional<Conference> findByTitle(String title);

    Optional<Conference> findByTitleAndIdNot(String title, Long id);
}
