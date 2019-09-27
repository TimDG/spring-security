package be.continuum.springsecurity.user;

import be.continuum.springsecurity.register.RegistrationUser;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BlogUserDetailService implements UserDetailsService {

    private final BlogUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public BlogUserDetailService(BlogUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No such user " + email));
    }

    public void register(RegistrationUser user) {

        if (repository.existsByEmail(user.getEmail())) {
            //Execute the slow code to prevent timing attacks.
            passwordEncoder.encode(user.getPassword());
            return;
        }

        BlogUser blogUser = new BlogUser();
        blogUser.setEmail(user.getEmail());
        blogUser.setName(user.getName());
        blogUser.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(blogUser);
    }
}
