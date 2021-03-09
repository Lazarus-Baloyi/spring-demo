package za.co.laz.springdemo.infrastructure;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@Repository
public interface ICustomerRepository extends CrudRepository<Customer, UUID> {

}