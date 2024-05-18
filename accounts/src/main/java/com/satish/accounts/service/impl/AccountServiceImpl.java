package com.satish.accounts.service.impl;

import com.satish.accounts.constants.AccountConstants;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

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
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Annonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       // Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Optional<Customer> existCustomer = customerRepository.findByMobileNumber(mobileNumber);
        Customer customer = existCustomer.get();
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow( () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
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

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Annonymous");
        return newAccount;
    }
}
