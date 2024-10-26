package com.satish.accounts.service.impl;

import com.satish.accounts.dto.AccountsDto;
import com.satish.accounts.dto.CardsDto;
import com.satish.accounts.dto.CustomerDetailsDto;
import com.satish.accounts.dto.LoansDto;
import com.satish.accounts.entity.Accounts;
import com.satish.accounts.entity.Customer;
import com.satish.accounts.exception.ResourceNotFoundException;
import com.satish.accounts.mapper.AccountsMapper;
import com.satish.accounts.mapper.CustomerMapper;
import com.satish.accounts.repository.AccountsRepository;
import com.satish.accounts.repository.CustomerRepository;
import com.satish.accounts.service.ICustomerService;
import com.satish.accounts.service.client.CardsFeignClient;
import com.satish.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Class CustomerServiceImpl
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 26/10/24
 */
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow( () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer , new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity  = cardsFeignClient.fetchCardDetails(mobileNumber);

        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
