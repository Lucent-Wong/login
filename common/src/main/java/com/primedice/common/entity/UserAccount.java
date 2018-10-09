package com.primedice.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.primedice.common.exceptions.InsufficientDepositException;
import com.primedice.common.exceptions.InsufficientBalanceException;
import com.primedice.common.exceptions.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccount {
    Long userId;
    Long deposit;
    Long ethBalance;
    String description;
    String wallet;
    String walletSecret;
    Boolean available;

    @JsonIgnore
    public UserAccount withdraw(long value) throws InvalidValueException, InsufficientDepositException {
        synchronized (this) {
            decreaseDeposit(value);
            increaseEthBalance(value);
        }
        return this;
    }

    @JsonIgnore
    public UserAccount decreaseEthBalance(long value) throws InvalidValueException, InsufficientBalanceException {
        synchronized (this) {
            if (value < 0) {
                throw new InvalidValueException("Invalid value for the value: " + value);
            }
            if (ethBalance > value) {
                ethBalance = ethBalance - value;
            } else {
                throw new InsufficientBalanceException("InsufficientBalance of users");
            }
        }
        return  this;
    }

    @JsonIgnore
    public UserAccount increaseEthBalance(long value) throws InvalidValueException {
        synchronized (this) {
            if (value < 0) {
                throw new InvalidValueException("Invalid value for the value: " + value);
            }
            if (Long.MAX_VALUE - value < ethBalance) {
                ethBalance = ethBalance + value;
            } else {
                throw new InvalidValueException("Eth balance will exceed for increase value: " + value);
            }
        }
        return this;
    }

    @JsonIgnore
    public UserAccount decreaseDeposit(long value) throws InvalidValueException, InsufficientDepositException {
        synchronized (this) {
            if (value < 0) {
                throw new InvalidValueException("Invalid value for the value: " + value);
            }
            if (deposit > value) {
                deposit = deposit - value;
            } else {
                throw new InsufficientDepositException("InsufficientDeposit of users");
            }
        }
        return  this;
    }

    @JsonIgnore
    public UserAccount increaseDeposit(long value) throws InvalidValueException {
        synchronized (this) {
            if (value < 0) {
                throw new InvalidValueException("Invalid value for the value: " + value);
            }
            if (Long.MAX_VALUE - value < deposit) {
                 deposit = deposit + value;
            } else {
                throw new InvalidValueException("Eth balance will exceed for increase value: " + value);
            }
        }
        return this;
    }
}
