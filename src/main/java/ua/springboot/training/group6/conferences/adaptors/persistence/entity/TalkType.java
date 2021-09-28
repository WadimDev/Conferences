package ua.springboot.training.group6.conferences.adaptors.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "talk_types")
public class TalkType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talk_types_id_seq")
    @SequenceGenerator(name = "talk_types_id_seq", sequenceName = "talk_types_id_seq", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String type;
}
