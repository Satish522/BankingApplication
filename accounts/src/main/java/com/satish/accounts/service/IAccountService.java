package com.satish.accounts.service;

import com.satish.accounts.dto.CustomerDto;

public interface IAccountService {


    /**
     * @param customer - Customer Object
     */
    void createAccount(CustomerDto customer);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

    boolean updateCommunicatonStatus(Long accountNumber);
}
