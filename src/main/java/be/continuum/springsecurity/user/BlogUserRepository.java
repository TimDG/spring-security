package be.continuum.springsecurity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

    Optional<BlogUser> findByEmail(String email);
}
