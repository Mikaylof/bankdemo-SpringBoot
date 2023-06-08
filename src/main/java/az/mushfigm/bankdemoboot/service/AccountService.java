package az.mushfigm.bankdemoboot.service;

import az.mushfigm.bankdemoboot.dto.request.ReqAccount;
import az.mushfigm.bankdemoboot.dto.response.RespAccount;
import az.mushfigm.bankdemoboot.dto.response.Response;

import java.util.List;

public interface AccountService {
    Response<List<RespAccount>> getAccountListByCustomerId(Long customerId);

    Response createAccount(ReqAccount reqAccount);

}
