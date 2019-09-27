package be.continuum.springsecurity.role;

import org.springframework.data.jpa.repository.JpaRepository;

interface BlogRoleRepository extends JpaRepository<BlogRole, Long> {

    BlogRole findByName(String name);
}
