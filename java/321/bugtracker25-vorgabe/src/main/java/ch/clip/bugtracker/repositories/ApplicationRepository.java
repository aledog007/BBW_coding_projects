package ch.clip.bugtracker.repositories;

import ch.clip.bugtracker.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
}
