package com.satish.accounts.service;

import com.satish.accounts.dto.CustomerDetailsDto;

/**
 * Class ICustomerService
 * <p>
 * Description: This class represents ...
 * </p>
 *
 * @author satishkumarsubudhi
 * @since 26/10/24
 */
public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
