package sena.activitytracker.acktrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActrackApplication.class, args);
		System.out.println("Hello Activities Track");
	}

}
