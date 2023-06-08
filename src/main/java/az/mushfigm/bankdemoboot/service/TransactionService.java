package az.mushfigm.bankdemoboot.service;

import az.mushfigm.bankdemoboot.dto.request.ReqTransaction;
import az.mushfigm.bankdemoboot.dto.response.RespTransaction;
import az.mushfigm.bankdemoboot.dto.response.Response;

import java.util.List;

public interface TransactionService {
    Response<List<RespTransaction>> getTransactionList(Long accountId);

    Response createOperation(ReqTransaction reqTransaction);
}
