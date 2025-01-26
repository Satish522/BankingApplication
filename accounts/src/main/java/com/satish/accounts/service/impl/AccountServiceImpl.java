package com.satish.accounts.service.impl;

import com.satish.accounts.constants.AccountConstants;
import com.satish.accounts.dto.AccountMsgSto;
import com.satish.accounts.dto.AccountsDto;
import com.satish.accounts.dto.CustomerDto;
import com.satish.accounts.entity.Accounts;
import com.satish.accounts.entity.Customer;
import com.satish.accounts.exception.CustomerAlreadyExistException;
import com.satish.accounts.exception.ResourceNotFoundException;
import com.satish.accounts.mapper.AccountsMapper;
import com.satish.accounts.mapper.CustomerMapper;
import com.satish.accounts.repository.AccountsRepository;
import com.satish.accounts.repository.CustomerRepository;
import com.satish.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private final StreamBridge streamBridge;

    /**
     * @param customerDto - Customer Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> existCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(!existCustomer.isEmpty()){
            throw new CustomerAlreadyExistException("Customer Already Exist with given mobile Number "+ customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);

        Accounts savedAccounts =  accountsRepository.save(createNewAccount(savedCustomer));

        sendCommunication(savedAccounts, savedCustomer);
    }



    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow( () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow( ()-> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(()-> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customer = customerRepository.save(customer);
            isUpdate = true;
        }

        return isUpdate;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Customer", "CustomerId", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicatonStatus(Long accountNumber) {
        if(accountNumber != null) {
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "AccoutNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            return true;
        }
        return false;
    }

    /**
     * @param customer
     * @return
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVING);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }

    private void sendCommunication(Accounts accounts, Customer customer) {
        var accountsMsgDto = new AccountMsgSto(accounts.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        log.info("Sending communication for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the communication request successfully  processed?: {}", result);
    }

}
