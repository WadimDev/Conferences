package ua.springboot.training.group6.conferences.adaptors.persistence.entity;

import lombok.*;
import org.modelmapper.ModelMapper;
import ua.springboot.training.group6.conferences.domain.TalkRequest;
import ua.springboot.training.group6.conferences.domain.TalkResponse;

import javax.persistence.*;

// названием,           -	доклады уникальны по названию
// описанием,
// именем докладчика      -	докладчик не может подать больше 3 докладов
// типом доклада (доклад, мастер-класс, воркшоп)
// подача докладов разрешена не позже чем за месяц до начала конференции

// create sequence talks_id_seq;

//все поля у конференции и доклада обязательные и должны быть не пустыми
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
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

//    @Column(name = "conference_id")
//    private Long conferenceId;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TalkType talkType;

    public static Talk of(TalkRequest talkRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(talkRequest, Talk.class);//FIXME
    }

    public TalkResponse toTalkResponse() {
        var talkResponse = new TalkResponse();
        talkResponse.setId(id);
        talkResponse.setTalkType(talkType.getType());
        talkResponse.setDescription(getDescription());
        talkResponse.setTitle(getTitle());
        talkResponse.setRapporteurName(getRapporteurName());

        return talkResponse;
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(this, TalkResponse.class);//FIXME
    }
}
