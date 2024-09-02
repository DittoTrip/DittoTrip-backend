package site.dittotrip.ditto_trip.alarm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Alarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private String title;
    private String body;
    private String path;

    @Setter
    private Boolean isChecked = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public Alarm(String title, String body, String path, User user) {
        this.title = title;
        this.body = body;
        this.path = path;
        this.user = user;
    }

    public static List<Alarm> createAlarms(String title, String body, String path, List<User> targets) {
        List<Alarm> alarms = new ArrayList<>();
        for (User target : targets) {
            alarms.add(new Alarm(title, body, path, target));
        }
        return alarms;
    }

}
