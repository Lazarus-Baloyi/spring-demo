package za.co.laz.springdemo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer  {

    @Id
    private long ucn;

    private String name;
    private String email;
    private long accountNumber;
    private String cellNumber;
}
