package ua.springboot.training.group6.conferences.adaptors.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.springboot.training.group6.conferences.domain.Conference;

public interface ConferenceDao extends JpaRepository<Conference, Long> {


}
