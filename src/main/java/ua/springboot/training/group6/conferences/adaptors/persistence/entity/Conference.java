package ua.springboot.training.group6.conferences.adaptors.persistence.entity;

import lombok.*;
import org.modelmapper.ModelMapper;
import ua.springboot.training.group6.conferences.domain.ConferenceRequest;
import ua.springboot.training.group6.conferences.domain.ConferenceResponse;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "conferences")
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

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(AccessLevel.PRIVATE)
    private List<Talk> talks = new ArrayList<>();

    public void addTalk(Talk talk) {
        talks.add(talk);
        talk.setConference(this);
    }

    public static Conference of(ConferenceRequest conferenceRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(conferenceRequest, Conference.class);//FIXME
    }

    public ConferenceResponse toConferenceResponse() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConferenceResponse.class);
    }
}
