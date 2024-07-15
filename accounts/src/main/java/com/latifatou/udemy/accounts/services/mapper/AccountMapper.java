package com.latifatou.udemy.accounts.services.mapper;

import com.latifatou.udemy.accounts.dto.AccountDto;
import com.latifatou.udemy.accounts.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account accounts, AccountDto accountDto) {
        accountDto.setAccountNumber(accounts.getAccountNumber());
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBranchAddress(accounts.getBranchAddress());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }


}
