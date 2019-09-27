package be.continuum.springsecurity.lostpass;

import be.continuum.springsecurity.user.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    Optional<PasswordToken> findByToken(String token);

    void deleteByBlogUser(BlogUser user);
}
