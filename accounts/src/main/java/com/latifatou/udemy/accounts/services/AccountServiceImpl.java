package com.latifatou.udemy.accounts.services;

import com.latifatou.udemy.accounts.constants.AccountConstants;
import com.latifatou.udemy.accounts.dto.AccountDto;
import com.latifatou.udemy.accounts.dto.CustomerDto;
import com.latifatou.udemy.accounts.entity.Account;
import com.latifatou.udemy.accounts.entity.Customer;
import com.latifatou.udemy.accounts.exception.CustomerAlreadyExistsException;
import com.latifatou.udemy.accounts.exception.RessourceNotFoundException;
import com.latifatou.udemy.accounts.repository.AccountRepository;
import com.latifatou.udemy.accounts.repository.CustomerRepository;
import com.latifatou.udemy.accounts.services.mapper.AccountMapper;
import com.latifatou.udemy.accounts.services.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customerOptional.isPresent()){
            throw new CustomerAlreadyExistsException("CustomerAlready registered with given mobileNumber: " +customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }



    public Account createNewAccount(Customer customer){
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new RessourceNotFoundException("customer", "mobileNumber", mobileNumber)
                );
       Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
               ()-> new RessourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
       );
       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto !=null ){
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new RessourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccount(accountDto, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new RessourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new RessourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
