package ch.clip.bugtracker.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
