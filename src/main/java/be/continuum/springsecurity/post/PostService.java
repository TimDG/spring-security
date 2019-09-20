package be.continuum.springsecurity.post;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    Collection<Post> getPosts() {
        return repository.findAllByDeletedOrderByCreatedDesc(false);
    }

    Collection<Post> getDeletedPosts() {
        return repository.findAllByDeletedOrderByCreatedDesc(true);
    }

    Post getPost(String externalId) {
        return repository.findByExternalId(externalId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public Post create(Post post) {
        return repository.save(post);
    }

    @Transactional
    public void delete(String externalId) {
        Post post = repository.findByExternalId(externalId).orElseThrow(PostNotFoundException::new);
        post.setDeleted(true);
        repository.save(post);
    }

    @Transactional
    public void update(String externalId, Post post) {
        Post current = repository.findByExternalId(externalId).orElseThrow(PostNotFoundException::new);
        current.update(post);
        repository.save(current);
    }
}
