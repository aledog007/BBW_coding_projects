package ch.clip.bugtracker.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class UserManagementApplication {

    private final Logger log = Logger.getLogger(String.valueOf(UserManagementApplication.class));

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(PersonRepository personRepository) {
        return (args) -> {
            Person p1 = new Person("Mary", "Richards", "QA Engineer");
            Person p2 = new Person("Toya", "Stewart", "Product Owner");
            Person p3 = new Person("Jeff", "Stone", "Lead Developer");
            Person p4 = new Person("Dean", "Toms", "DevOps Engineer");

            personRepository.save(p1);
            personRepository.save(p2);
            personRepository.save(p3);
            personRepository.save(p4);

            log.info("Saved Person id: " + p1.getId());
            log.info("Saved Person id: " + p2.getId());
            log.info("Saved Person id: " + p3.getId());
            log.info("Saved Person id: " + p4.getId());
        };
    }
}
