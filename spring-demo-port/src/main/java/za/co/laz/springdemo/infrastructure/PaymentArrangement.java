package za.co.laz.springdemo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PaymentArrangement {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private long fromaccountNumber;
    private long toaccountNumber;
    private Double currentInstallment;
    private Double arrearsAmount;
    private Double paymentTermAmount;
    private Double totalRepayment;
    private int paymentTermPeriod;
    private int remainingTermPeriod;
    private String paymentTermStartingMonth;
    private String paymentTermEndingMonth;
    private String reasonInArrears;
    private Date paymentDay;
}
