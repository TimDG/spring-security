package be.continuum.springsecurity.role;

import org.springframework.stereotype.Service;

import static be.continuum.springsecurity.role.Roles.READER;

@Service
public class BlogRoleService {


    private final BlogRoleRepository repository;

    public BlogRoleService(BlogRoleRepository repository) {
        this.repository = repository;
    }

    public BlogRole getReader() {
        return repository.findByName(READER.name());
    }
}
