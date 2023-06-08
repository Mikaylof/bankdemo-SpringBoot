package az.mushfigm.bankdemoboot.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqAccount {
    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private Integer branchCode;
    private Long customerId;
}
