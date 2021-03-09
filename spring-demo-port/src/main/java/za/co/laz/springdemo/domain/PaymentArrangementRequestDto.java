package za.co.laz.springdemo.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class PaymentArrangementRequestDto {

    private long fromaccountNumber;
    private long toaccountNumber;
    private Double arrangementAmount;
    private String paymentDay;

}
