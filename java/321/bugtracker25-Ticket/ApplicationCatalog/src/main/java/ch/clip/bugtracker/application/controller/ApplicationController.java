package ch.clip.bugtracker.application.controller;

import ch.clip.bugtracker.application.Application;
import ch.clip.bugtracker.application.ApplicationRepository;
import ch.clip.bugtracker.application.dto.ApplicationDetailDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8083/users/";

    public ApplicationController(ApplicationRepository applicationRepository, RestTemplate restTemplate) {
        this.applicationRepository = applicationRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getApplicationDetails(@PathVariable Integer id) {
        Optional<Application> appOpt = applicationRepository.findById(id);
        if (appOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
        }

        Application app = appOpt.get();
        String ownerName = "Unknown";
        String ownerRole = "Unknown";

        if (app.getOwner_id() != null) {
            try {
                // Synchroner REST-Aufruf zum UserManagement-Microservice (Port 8083)
                JsonNode userJson = restTemplate.getForObject(USER_SERVICE_URL + app.getOwner_id(), JsonNode.class);
                if (userJson != null) {
                    String firstName = userJson.has("firstName") ? userJson.get("firstName").asText() : "";
                    String lastName = userJson.has("lastName") ? userJson.get("lastName").asText() : "";
                    ownerName = (firstName + " " + lastName).trim();
                    if (userJson.has("role")) {
                        ownerRole = userJson.get("role").asText();
                    }
                }
            } catch (ResourceAccessException e) {
                // Tritt auf, wenn UserManagement nicht erreichbar ist (Connection Refused)
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("UserManagement-Service auf Port 8083 ist nicht erreichbar: " + e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Fehler beim Abrufen der Benutzerdaten: " + e.getMessage());
            }
        }

        ApplicationDetailDTO details = new ApplicationDetailDTO(
                app.getId(),
                app.getName(),
                app.getDescription(),
                app.getOwner_id(),
                ownerName,
                ownerRole
        );

        return ResponseEntity.ok(details);
    }
}
