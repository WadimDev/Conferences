package ua.springboot.training.group6.conferences.adaptors.persistence;

import org.springframework.data.repository.CrudRepository;

//FIXME CrudRepository: domain type, type of id
public interface ConferenceDao extends CrudRepository<String, String> {


}
