package ch.clip.bugtracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Integer id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String description;

    @Column(nullable = false)
    private Integer applicationId;

    @Column(nullable = false)
    private Integer personId;

    public Ticket(String title, String description, Integer applicationId, Integer personId) {

        this.title = title;
        this.description = description;
        this.applicationId = applicationId;
        this.personId = personId;
    }
}
