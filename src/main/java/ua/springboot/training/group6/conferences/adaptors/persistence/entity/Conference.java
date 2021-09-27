package ua.springboot.training.group6.conferences.adaptors.persistence.entity;

import lombok.*;
import org.modelmapper.ModelMapper;
import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// названием,   уникальны
// тематикой,
// датами проведения и
// количеством участников;

// все поля у конференции и доклада обязательные и должны быть не пустыми, количество участников > 100,
// даты конференций не должны пересекаться, иначе возвращаться 400 HTTP статус;

// create sequence conferences_id_seq;
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "conferences")
//@RequiredArgsConstructor
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conferences_id_seq")
    @SequenceGenerator(name = "conferences_id_seq", sequenceName = "conferences_id_seq", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String theme;

    @Column(columnDefinition = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "participants_count", columnDefinition = "int", nullable = false)
    private int participantsCount;

//        @OneToMany
//    @JoinTable(name = "talks",
//            joinColumns = {@JoinColumn(name = "conference_id")})//,
////            inverseJoinColumns = {@JoinColumn(name = "id")})
//    private Set<Talk> talks;

    //        @JoinColumn(name = "conference_id")
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    private List<Talk> talks = new ArrayList<>();

//    @JoinColumn(name = "conference_id")
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @Setter(AccessLevel.PRIVATE)
//    private List<Talk> talks = new ArrayList<>();

    public void addTalk(Talk talk) {
        talks.add(talk);
        talk.setConference(this);
    }

    public void removeProduct(Talk talk) {
        talk.setConference(null);
        talks.remove(talk);
    }

    public static Conference of(ConferenceRequest conferenceRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(conferenceRequest, Conference.class);//FIXME
    }

    public ConferenceResponse toConferenceResponse() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConferenceResponse.class);

//        return null;//FIXME
    }
}
