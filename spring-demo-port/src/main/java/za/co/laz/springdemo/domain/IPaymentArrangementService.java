package za.co.laz.springdemo.domain;

import org.springframework.stereotype.Component;

@Component
public interface IPaymentArrangementService {

    PaymentArrangementResponseDto loadPaymentArrangement(PaymentArrangementRequestDto paymentArrangementRequestDto);

    boolean deletePaymentArrangement(PaymentArrangementRequestDto paymentArrangementRequestDto);

    boolean updatePaymentArrangement (PaymentArrangementRequestDto paymentArrangementRequestDto);

    CustomerDto getCustomerDetails (long loanAccountNumber);
}
