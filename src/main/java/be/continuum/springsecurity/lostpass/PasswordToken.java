package be.continuum.springsecurity.lostpass;

import be.continuum.springsecurity.user.BlogUser;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Data
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    private String token;

    @OneToOne()
    @JoinColumn(name = "blog_user_id", referencedColumnName = "id")
    private BlogUser blogUser;

    @Setter(AccessLevel.NONE)
    private LocalDateTime created;

    public PasswordToken() {
        created = now();
        token = UUID.randomUUID().toString();
    }
}
