package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.shared.Registration;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByLastNameStartsWithIgnoreCase(String filterText);
	
	List<Customer> findByAgeStartsWithIgnoreCase(String filterText);
}
