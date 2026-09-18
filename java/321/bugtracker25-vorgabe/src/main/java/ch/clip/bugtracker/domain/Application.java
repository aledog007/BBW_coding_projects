package ch.clip.bugtracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "app")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "app_id")
    private Integer id;

    @Column(name = "app_name", nullable = false)
    private String name;

    @Column(length = 2000, nullable = false)
    private String description;

    private Integer owner_id;


    public Application(String name, Integer owner_id,
                       String description) {
        this.name = name;
        this.owner_id = owner_id;
        this.description = description;
    }

}
