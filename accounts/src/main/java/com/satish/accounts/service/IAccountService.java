package com.satish.accounts.service;

import com.satish.accounts.dto.CustomerDto;

public interface IAccountService {


    /**
     * @param customer - Customer Object
     */
    void createAccount(CustomerDto customer);

    CustomerDto fetchAccount(String mobileNumber);
}
