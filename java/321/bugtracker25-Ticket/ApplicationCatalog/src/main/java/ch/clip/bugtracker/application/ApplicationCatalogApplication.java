package ch.clip.bugtracker.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@SpringBootApplication
public class ApplicationCatalogApplication {

    private final Logger log = Logger.getLogger(String.valueOf(ApplicationCatalogApplication.class));

    public static void main(String[] args) {
        SpringApplication.run(ApplicationCatalogApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner demoData(ApplicationRepository applicationRepository) {
        return (args) -> {
            Application app1 = new Application("Bug Tracker", 1, "An app to track defects.");
            Application app2 = new Application("Project Tracker", 2, "An app to track project management tasks.");
            Application app3 = new Application("Release Manager", 3, "An app to manage software releases.");

            applicationRepository.save(app1);
            applicationRepository.save(app2);
            applicationRepository.save(app3);

            log.info("Saved App id: " + app1.getId());
            log.info("Saved App id: " + app2.getId());
            log.info("Saved App id: " + app3.getId());
        };
    }
}
