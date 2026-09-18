package ch.clip.bugtracker;

import org.springframework.boot.SpringApplication;

public class TestBugtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.from(BugtrackerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
