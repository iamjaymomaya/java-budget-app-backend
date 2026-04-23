package com.budget_app.requests;

import com.budget_app.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    private AccountType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
