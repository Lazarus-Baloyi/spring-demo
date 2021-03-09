package za.co.laz.springdemo.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface IPaymentArrangementRepository extends CrudRepository<PaymentArrangement, UUID> {


    PaymentArrangement findById(Long id);
}
