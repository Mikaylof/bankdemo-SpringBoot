package az.mushfigm.bankdemoboot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespAccount {
    private Long id;
    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private Integer branchCode;
    private RespCustomer respCustomer;
}
