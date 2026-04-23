package com.budget_app.responses;

import com.budget_app.enums.AccountType;

public class AccountResponse {

    private Long id;
    private String name;

    public AccountResponse() {
    }

    public AccountResponse(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
