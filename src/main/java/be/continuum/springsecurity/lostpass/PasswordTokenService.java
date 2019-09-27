package be.continuum.springsecurity.lostpass;

import be.continuum.springsecurity.user.BlogUser;
import be.continuum.springsecurity.user.BlogUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class PasswordTokenService {

    private final PasswordTokenRepository repository;
    private final BlogUserDetailService userDetailService;

    public PasswordTokenService(PasswordTokenRepository repository, BlogUserDetailService userDetailService) {
        this.repository = repository;
        this.userDetailService = userDetailService;
    }

    private Optional<PasswordToken> generatePasswordResetToken(String email) {
        return userDetailService.getUser(email)
                .map(user -> {
                    removeExistingToken(user);
                    PasswordToken token = new PasswordToken();
                    token.setBlogUser(user);
                    repository.save(token);
                    return token;
                });
    }

    private void removeExistingToken(BlogUser user) {
        repository.deleteByBlogUser(user);
    }

    @Transactional
    public void sendPasswordReset(String email) {
        Optional<PasswordToken> passwordToken = generatePasswordResetToken(email);

        if (passwordToken.isPresent()) {
            log.info("Password reset token: {}", passwordToken.get().getToken());
        } else {
            log.info("Send user a link to the registration page");
        }
    }

    void checkToken(String token) {
        var passwordToken = repository.findByToken(token);

        if (passwordToken.isPresent()) {
            LocalDateTime created = passwordToken.get().getCreated();
            if (now().isAfter(created.plusHours(3))) {
                throw new ExpiredTokenException();
            }
        } else {
            throw new InvalidTokenException(token);
        }
    }


    @Transactional
    public void updatePassword(String token, String password) {
        checkToken(token);
        var passwordToken = repository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException(token));

        userDetailService.updatePassword(passwordToken.getBlogUser(), password);
        repository.delete(passwordToken);
    }
}
