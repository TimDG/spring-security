package be.continuum.springsecurity.user;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BlogUserDetailService implements UserDetailsService {

    private final BlogUserRepository repository;

    public BlogUserDetailService(BlogUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No such user " + email));
    }
}
