package site.dittotrip.ditto_trip.ditto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Ditto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ditto_id")
    private Long id;

    private String title;
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;



}
