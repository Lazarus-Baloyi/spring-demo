package za.co.laz.springdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromiseToPay implements Serializable {

    private Date promiseDate;
    private Double promiseAmount;
}
