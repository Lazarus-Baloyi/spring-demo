package za.co.laz.springdemo.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PaymentArrangementResponseDto {
    private boolean loaded;
    private String response;
    private long id;
    private Boolean ilpPaymentArrangementSuccessful;
    private Boolean ocsPaymentArrangementSuccessful;
    private Boolean accountRemarkUpdateSuccessful;
    private Boolean pushMessageSendSuccessful;
}
