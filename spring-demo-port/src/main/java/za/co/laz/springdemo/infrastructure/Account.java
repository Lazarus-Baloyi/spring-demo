package za.co.laz.springdemo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Integer companyCode;
    private long accountNumber;
    private String product;
    private String status;
    private String subStatus;
    private Long dateOpened;
    private Long dateClosed;
    private Long dateLastMaintained;
    private String accountName;
    private Integer servicingBranchCode;
    private Integer openingBranchCode;
    private Integer relationshipBranchCode;
    private Boolean chargeMonthlyAdminFeeIndicator;
    private String monthlyAdminFeeType;
    private String currency;
    private Double prepaymentAmount;
    private Double payoffAmount;
    private Double interestAccrued;
    private Double arrearsAmount;
    private Double rescheduledArrearsAmount;
}
