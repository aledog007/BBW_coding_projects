package ch.clip.bugtracker.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "tickets", collectionResourceRel = "tickets")
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByTitle(@Param("title") String title);

    List<Ticket> findByDescriptionContaining(@Param("description") String description);
}
