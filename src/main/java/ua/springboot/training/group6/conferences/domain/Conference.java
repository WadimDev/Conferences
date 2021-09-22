package ua.springboot.training.group6.conferences.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Conference {
    @Id
    long id;


//    все поля у конференции и доклада обязательные и должны быть не пустыми, количество участников > 100,
//даты конференций не должны пересекаться, иначе возвращаться 400 HTTP статус;
}
