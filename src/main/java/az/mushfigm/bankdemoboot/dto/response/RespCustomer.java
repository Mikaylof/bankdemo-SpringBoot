package az.mushfigm.bankdemoboot.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
public class RespCustomer {
    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String pin;
    private String seria;
    private Date dob;
    private String phone;
    private String cif;
    private String email;
    private String activationCode;
    private Integer activationStatus;
}
