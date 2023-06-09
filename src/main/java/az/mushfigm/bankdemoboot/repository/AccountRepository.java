package az.mushfigm.bankdemoboot.repository;

import az.mushfigm.bankdemoboot.entity.Account;
import az.mushfigm.bankdemoboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findAllByCustomerAndActive(Customer customer, Integer active);
    Account findAccountByIdAndActive(Long id,Integer active);
}
