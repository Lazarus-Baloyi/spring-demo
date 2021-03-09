package za.co.laz.springdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class CollectionsPaymentResponseDto implements Serializable {

    private Boolean ilpPaymentArrangementSuccessful;
    private Boolean ocsPaymentArrangementSuccessful;
    private Boolean accountRemarkUpdateSuccessful;
    private Boolean pushMessageSendSuccessful;

}
