package be.continuum.springsecurity.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByExternalId(String externalId);

    Collection<Post> findAllByDeletedOrderByCreatedDesc(boolean deleted);
}
