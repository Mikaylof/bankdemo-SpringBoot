package az.mushfigm.bankdemoboot.controller;

import az.mushfigm.bankdemoboot.dto.request.ReqAccount;
import az.mushfigm.bankdemoboot.dto.response.RespAccount;
import az.mushfigm.bankdemoboot.dto.response.Response;
import az.mushfigm.bankdemoboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/GetAccountListByCustomerId/{customerId}")
    public Response<List<RespAccount>>getAccountListByCustomerId(@PathVariable Long customerId){
        return accountService.getAccountListByCustomerId(customerId);
    }
    @PostMapping("/CreateAccount")
    public Response createAccount(@RequestBody ReqAccount reqAccount){
        return accountService.createAccount(reqAccount);
    }

}
