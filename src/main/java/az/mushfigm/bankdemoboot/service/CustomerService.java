package az.mushfigm.bankdemoboot.service;

import az.mushfigm.bankdemoboot.dto.request.ReqCustomer;
import az.mushfigm.bankdemoboot.dto.request.ReqToken;
import az.mushfigm.bankdemoboot.dto.response.RespCustomer;
import az.mushfigm.bankdemoboot.dto.response.Response;

import java.util.List;

public interface CustomerService {
    Response<List<RespCustomer>> getCustomerList(ReqToken reqToken);

    Response<RespCustomer> getCustomerById(ReqCustomer reqCustomer);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(ReqCustomer reqCustomer);

    Response activateCustomer(String activationCode);
}
