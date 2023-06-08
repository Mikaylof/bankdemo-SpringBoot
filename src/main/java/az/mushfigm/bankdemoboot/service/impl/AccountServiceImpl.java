package az.mushfigm.bankdemoboot.service.impl;

import az.mushfigm.bankdemoboot.dto.request.ReqAccount;
import az.mushfigm.bankdemoboot.dto.response.RespAccount;
import az.mushfigm.bankdemoboot.dto.response.RespCustomer;
import az.mushfigm.bankdemoboot.dto.response.RespStatus;
import az.mushfigm.bankdemoboot.dto.response.Response;
import az.mushfigm.bankdemoboot.entity.Account;
import az.mushfigm.bankdemoboot.entity.Customer;
import az.mushfigm.bankdemoboot.enums.EnumAvailableStatus;
import az.mushfigm.bankdemoboot.exception.BankException;
import az.mushfigm.bankdemoboot.exception.ExceptionConstants;
import az.mushfigm.bankdemoboot.repository.AccountRepository;
import az.mushfigm.bankdemoboot.repository.CustomerRepository;
import az.mushfigm.bankdemoboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespAccount>> getAccountListByCustomerId(Long customerId) {
        Response<List<RespAccount>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Account> accountList = accountRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
            }
            List<RespAccount> respAccountList = accountList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respAccountList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createAccount(ReqAccount reqAccount) {
        Response response=new Response();
        try{
            String name = reqAccount.getName();
            String accountNo = reqAccount.getAccountNo();
            String iban = reqAccount.getIban();
            Integer branchCode = reqAccount.getBranchCode();
            Long customerId = reqAccount.getCustomerId();
            String currency = reqAccount.getCurrency();
            if(name == null || accountNo == null || iban == null || customerId == null || currency == null || branchCode == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.value);
            if(customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
            }
            Account account= Account.builder()
                    .name(name)
                    .accountNo(accountNo)
                    .iban(iban)
                    .branchCode(branchCode)
                    .currency(currency)
                    .customer(customer)
                    .build();
            accountRepository.save(account);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (BankException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    private RespAccount mapping(Account account) {
        RespCustomer respCustomer=RespCustomer.builder()
                .name(account.getCustomer().getName())
                .surname(account.getCustomer().getSurname())
                .build();
        RespAccount respAccount = RespAccount.builder()
                .id(account.getId())
                .name(account.getName())
                .accountNo(account.getAccountNo())
                .iban(account.getIban())
                .branchCode(account.getBranchCode())
                .currency(account.getCurrency())
                .respCustomer(respCustomer)
                .build();
        return respAccount;
    }


}
