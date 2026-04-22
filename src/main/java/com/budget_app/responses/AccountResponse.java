package com.budget_app.responses;

import com.budget_app.enums.AccountType;

public class AccountResponse {

    private Long id;
    private String name;
    private AccountType type;

    public AccountResponse() {
    }

    public AccountResponse(Long id, String name, AccountType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
