package za.co.laz.springdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CollectionsPaymentRequestDto implements Serializable {
    private Double rescheduleAmount;
    private Double paymentTermAmount;
    private int paymentTermPeriod; // # months
    private String reasonInArrears;
    private List<PromiseToPay> promisesToPay;
    private String productCode;
    private Double totalRepaymentAmount;
}
