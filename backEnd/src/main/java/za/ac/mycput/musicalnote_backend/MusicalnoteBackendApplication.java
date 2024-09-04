package za.ac.mycput.musicalnote_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class MusicalnoteBackendApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MusicalnoteBackendApplication.class);
		application.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/backend"));
		application.run(args);
	}

}
