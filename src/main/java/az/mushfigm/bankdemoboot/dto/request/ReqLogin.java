package az.mushfigm.bankdemoboot.dto.request;

import lombok.Data;

@Data
public class ReqLogin {
    private String username;
    private String password;
}
