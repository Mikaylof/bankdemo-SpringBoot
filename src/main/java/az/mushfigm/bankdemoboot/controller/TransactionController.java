package az.mushfigm.bankdemoboot.controller;

import az.mushfigm.bankdemoboot.dto.request.ReqTransaction;
import az.mushfigm.bankdemoboot.dto.response.RespTransaction;
import az.mushfigm.bankdemoboot.dto.response.Response;
import az.mushfigm.bankdemoboot.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/GetTransactionList/{accountId}")
    public Response<List<RespTransaction>>getTransactionList(@PathVariable Long accountId){
        return transactionService.getTransactionList(accountId);
    }
    @PostMapping("/CreateOperation")
    public Response createOperation(@RequestBody ReqTransaction reqTransaction){
        return transactionService.createOperation(reqTransaction);
    }
}
