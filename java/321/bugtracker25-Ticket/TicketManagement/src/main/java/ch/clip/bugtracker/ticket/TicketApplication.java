package ch.clip.bugtracker.ticket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class TicketApplication {
	private Logger log = Logger.getLogger(String.valueOf(TicketApplication.class));


	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}


	@Bean
	public CommandLineRunner demoData(TicketRepository ticketRepository) {
		// https://spring.io/guides/gs/accessing-data-jpa/
		return (args) -> {

			Ticket tick1 = new Ticket("Add the ability to sort by release date", "The users need this new feature", 1, 2);
			Ticket tick2 = new Ticket("Updates are not saved correctly to task name", "This is a bug impacting this feature in production", 1, 2);
			Ticket tick3 = new Ticket("Provide a list of applications for users", "The users need this new feature", 2, 1);
			Ticket tick4 = new Ticket("Provide a list of tickets for an application", "The users need this new feature", 2, 1);
			Ticket tick5 = new Ticket("Provide a list of tickets for a given release", "The users need this new feature", 3, 4);
			Ticket tick6 = new Ticket("Give user the ability to add tickets to a release", "The users need this new feature", 4, 5);

			ticketRepository.save(tick1);
			ticketRepository.save(tick2);
			ticketRepository.save(tick3);
			ticketRepository.save(tick4);
			ticketRepository.save(tick5);
			ticketRepository.save(tick6);

			ticketRepository.save(new Ticket("Add the ability to sort by release date", "The users need this new feature", 1, 2));
			ticketRepository.save(new Ticket("Updates are not saved correctly to task name", "This is a bug impacting this feature in production", 1, 2));
			ticketRepository.save(new Ticket("Provide a list of applications for users", "The users need this new feature", 2, 1));
			ticketRepository.save(new Ticket("Provide a list of tickets for an application", "The users need this new feature", 2, 1));
			ticketRepository.save(new Ticket("Provide a list of tickets for a given release", "The users need this new feature", 3, 4));
			ticketRepository.save(new Ticket("Give user the ability to add tickets to a release", "The users need this new feature", 4, 5));
			ticketRepository.save(new Ticket("Add the ability to sort by release date", "The users need this new feature", 1, 2));
			ticketRepository.save(new Ticket("Updates are not saved correctly to task name", "This is a bug impacting this feature in production", 1, 2));
			ticketRepository.save(new Ticket("Provide a list of applications for users", "The users need this new feature", 2, 1));
			ticketRepository.save(new Ticket("Provide a list of tickets for an application", "The users need this new feature", 2, 1));
			ticketRepository.save(new Ticket("Provide a list of tickets for a given release", "The users need this new feature", 3, 4));
			ticketRepository.save(new Ticket("Give user the ability to add tickets to a release", "The users need this new feature", 4, 5));
			ticketRepository.save(new Ticket("Add the ability to sort by release date", "The users need this new feature", 1, 2));


			log.info("Saved Ticket id: " + tick1.getId());
			log.info("Saved Ticket id: " + tick2.getId());
			log.info("Saved Ticket id: " + tick3.getId());
			log.info("Saved Ticket id: " + tick4.getId());
			log.info("Saved Ticket id: " + tick5.getId());
			log.info("Saved Ticket id: " + tick6.getId());

		};
	}

}
