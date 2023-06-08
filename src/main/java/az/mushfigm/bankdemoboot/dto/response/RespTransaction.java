package az.mushfigm.bankdemoboot.dto.response;

import az.mushfigm.bankdemoboot.entity.Account;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
public class RespTransaction {
    private Long id;
    private RespAccount dtAccount;
    private String crAccount;
    private Double amount;
    private Double commission;
    private String currency;
    private Date trDate;
}
