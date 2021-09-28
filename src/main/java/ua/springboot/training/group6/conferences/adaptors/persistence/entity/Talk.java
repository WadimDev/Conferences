package ua.springboot.training.group6.conferences.adaptors.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "talks")
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talks_id_seq")
    @SequenceGenerator(name = "talks_id_seq", sequenceName = "talks_id_seq", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String description;

    @Column(name = "rapporteur_name", columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String rapporteurName;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TalkType talkType;

    public static Talk of(TalkRequest talkRequest) {
        var talk = new Talk();
        talk.setDescription(talkRequest.getDescription());
        talk.setTitle(talkRequest.getTitle());
        talk.setRapporteurName(talkRequest.getRapporteurName());


        return talk;
    }

    public TalkResponse toTalkResponse() {
        var talkResponse = new TalkResponse();
        talkResponse.setId(id);
        talkResponse.setTalkType(getTalkType().getType());
        talkResponse.setDescription(getDescription());
        talkResponse.setTitle(getTitle());
        talkResponse.setRapporteurName(getRapporteurName());

        return talkResponse;
    }
}
