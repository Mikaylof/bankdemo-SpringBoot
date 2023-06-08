package az.mushfigm.bankdemoboot.repository;

import az.mushfigm.bankdemoboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByActive(Integer active);

    Customer findCustomerByIdAndActive(Long id,Integer active);

    Customer findByEmailAndActive(String email, Integer active);

    Customer findByActivationCodeAndActive(String activationCode, int value);
}
