package az.mushfigm.bankdemoboot.dto.request;

import az.mushfigm.bankdemoboot.dto.response.RespAccount;
import az.mushfigm.bankdemoboot.entity.Account;
import lombok.Data;

import java.util.Date;

@Data
public class ReqTransaction {
    private Long dtAccountId;
    private String crAccount;
    private Double amount;
    private Double commission;
    private String currency;
    }
