package site.dittotrip.ditto_trip.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 임시 엔티티
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

}
