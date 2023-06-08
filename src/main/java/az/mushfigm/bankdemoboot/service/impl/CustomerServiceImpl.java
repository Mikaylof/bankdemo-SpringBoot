package az.mushfigm.bankdemoboot.service.impl;

import az.mushfigm.bankdemoboot.dto.request.ReqCustomer;
import az.mushfigm.bankdemoboot.dto.request.ReqToken;
import az.mushfigm.bankdemoboot.dto.response.RespCustomer;
import az.mushfigm.bankdemoboot.dto.response.RespStatus;
import az.mushfigm.bankdemoboot.dto.response.Response;
import az.mushfigm.bankdemoboot.entity.Customer;
import az.mushfigm.bankdemoboot.enums.EnumAvailableStatus;
import az.mushfigm.bankdemoboot.exception.BankException;
import az.mushfigm.bankdemoboot.exception.ExceptionConstants;
import az.mushfigm.bankdemoboot.repository.CustomerRepository;
import az.mushfigm.bankdemoboot.service.CustomerService;
import az.mushfigm.bankdemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final Utility utility;

    @Override
    public Response<List<RespCustomer>> getCustomerList(ReqToken reqToken) {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            utility.checkToken(reqToken);
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (customerList.isEmpty()) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<RespCustomer> respCustomerList = customerList.stream().map(this::mapping).collect(Collectors.toList());
            /*Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    utility.sendEmail("mikayilzademusviq98@gmail.com","Hello","Hello Mushfig");
                    System.out.println("Success");
                }
            };*/
            // runnable.run();
            // utility.sendEmail("mikayilzademusviq9@gmail.com","1");
            response.setT(respCustomerList);
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
    public Response<RespCustomer> getCustomerById(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            Long customerId = reqCustomer.getCustomerId();
            utility.checkToken(reqCustomer.getReqToken());
            if (customerId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = mapping(customer);
            response.setT(respCustomer);
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
    public Response addCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            utility.checkToken(reqCustomer.getReqToken());
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null && surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            if (reqCustomer.getEmail() == null) {
                throw new IllegalArgumentException("Recipient email not found");
            }
            Customer existingCustomer = customerRepository.findByEmailAndActive(reqCustomer.getEmail(), EnumAvailableStatus.ACTIVE.value);
            /*if (existingCustomer != null) {
                throw new BankException(ExceptionConstants.CUSTOMER_ALREADY_EXISTS, "Customer with the same email already exists");
            }*/
            Customer customer = Customer.builder().
                    name(name).
                    surname(surname).
                    address(reqCustomer.getAddress()).
                    phone(reqCustomer.getPhone()).
                    dob(reqCustomer.getDob()).
                    pin(reqCustomer.getPin()).
                    seria(reqCustomer.getSeria()).
                    cif(reqCustomer.getCif()).
                    email(reqCustomer.getEmail()).
                    activationCode(UUID.randomUUID().toString()).
                    activationStatus(reqCustomer.getActivationStatus()).
                    build();
            customerRepository.save(customer);
            utility.sendEmail(customer.getEmail(), customer.getActivationCode());
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
    public Response updateCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            utility.checkToken(reqCustomer.getReqToken());
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            Long customerId = reqCustomer.getCustomerId();
            if (name == null || surname == null || customerId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setName(name);
            customer.setSurname(surname);
            customer.setAddress(reqCustomer.getAddress());
            customer.setDob(reqCustomer.getDob());
            customer.setPhone(reqCustomer.getPhone());
            customer.setPin(reqCustomer.getPin());
            customer.setSeria(reqCustomer.getSeria());
            customer.setCif(reqCustomer.getCif());
            customerRepository.save(customer);
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
    public Response deleteCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            Long customerId = reqCustomer.getCustomerId();
            utility.checkToken(reqCustomer.getReqToken());
            if (customerId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.value);
            customerRepository.save(customer);
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
    public Response activateCustomer(String activationCode) {
        Response response = new Response<>();
        try {
            Customer customer = customerRepository.findByActivationCodeAndActive(activationCode, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new BankException(ExceptionConstants.INVALID_ACTIVATION_CODE, "Invalid activation code");
            }
            customer.setActivationStatus(EnumAvailableStatus.INPROGRESS.value);
            customerRepository.save(customer);
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

    private RespCustomer mapping(Customer customer) {
        RespCustomer respCustomer = RespCustomer.builder().
                customerId(customer.getId()).
                name(customer.getName()).
                surname(customer.getSurname()).
                phone(customer.getPhone()).
                pin(customer.getPin()).
                seria(customer.getSeria()).
                dob(customer.getDob()).
                address(customer.getAddress()).
                cif(customer.getCif()).
                build();
        return respCustomer;
    }


}
