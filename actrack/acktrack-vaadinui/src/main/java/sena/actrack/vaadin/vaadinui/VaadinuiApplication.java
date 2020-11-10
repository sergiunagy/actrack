package sena.actrack.vaadin.vaadinui;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.User;
import sena.activitytracker.acktrack.repositories.ProjectRepository;
import sena.activitytracker.acktrack.services.ActivityServiceImpl;
import sena.activitytracker.acktrack.services.ProjectService;
import sena.activitytracker.acktrack.services.ProjectServiceImpl;
import sena.activitytracker.acktrack.services.UserServiceImpl;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //todo enable security after configuration
@ComponentScan(basePackages={"sena.activitytracker.acktrack", "sena.actrack.vaadin"})
@EnableJpaRepositories(basePackages = "sena.activitytracker.acktrack")
@EntityScan(basePackages = {"sena.activitytracker.acktrack"})
public class VaadinuiApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VaadinuiApplication.class, args);

		log.info("VAADIN: Spring boot initializer..");
	}
}
