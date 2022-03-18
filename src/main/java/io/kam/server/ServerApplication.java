package io.kam.server;

import io.kam.server.enumeration.Status;
import io.kam.server.model.Server;
import io.kam.server.repo.ServerRepo;
import io.kam.server.service.implementation.ServerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	/*@Bean
	CommandLineRunner run(ServerServiceImpl service){
		return args -> {
			service.create(new Server(null, "192.168.1.1", "Ubunto Linux", "16 GB", "Personnal PC", "", Status.SERVER_UP));
			service.create(new Server(null, "192.168.1.2", "Fedora Linux", "32 GB", "Dell Tower", "", Status.SERVER_DOWN));
			service.create(new Server(null, "192.168.1.3", "Red Hat Enterprise Linux", "64 GB", "Mail Server", "", Status.SERVER_UP));
		};
	}*/

}
