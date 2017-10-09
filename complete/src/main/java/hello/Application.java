package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Customer("Ritesh", "Tiwary","22","8886858465","rt@gmail.com"));
			repository.save(new Customer("Harika", "Janamanchi","24","1234567890","hj@gmail.com"));
			repository.save(new Customer("Prashant", "Nair","30","1234543218","pn@gmail.com"));
			repository.save(new Customer("Avinash", "Kumar","28","1234387658","ak@gmail.com"));
			repository.save(new Customer("Divya", "Gajam","23","8965432789","dj@gmail.com"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository
					.findByLastNameStartsWithIgnoreCase("")) {
				log.info(bauer.toString());
			}
			for (Customer haire : repository
					.findByLastNameStartsWithIgnoreCase("")) {
				log.info(haire.toString());
			}
			log.info("");
		};
	}

}
