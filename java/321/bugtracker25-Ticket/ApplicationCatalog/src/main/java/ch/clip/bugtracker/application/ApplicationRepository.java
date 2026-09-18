package ch.clip.bugtracker.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "applications", collectionResourceRel = "applications")
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
