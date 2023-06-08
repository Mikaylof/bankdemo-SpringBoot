package az.mushfigm.bankdemoboot.repository;

import az.mushfigm.bankdemoboot.entity.Account;
import az.mushfigm.bankdemoboot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction>findAllByDtAccountAndActive(Account account, Integer active);
}
