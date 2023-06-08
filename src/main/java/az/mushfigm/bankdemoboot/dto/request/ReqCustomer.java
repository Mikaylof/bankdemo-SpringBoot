package az.mushfigm.bankdemoboot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ReqCustomer {
    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String pin;
    private String seria;
    private Date dob;
    private String phone;
    private String cif;
    @JsonProperty(value = "token")
    private ReqToken reqToken;
    private String email;
    private String activationCode;
    private Integer activationStatus;
}
