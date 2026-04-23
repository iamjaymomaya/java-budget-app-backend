package com.budget_app.responses;

import com.budget_app.enums.CategoryType;

import java.math.BigDecimal;

public class CategoryTotalResponse {

    private Long categoryId;
    private String categoryName;
    private CategoryType categoryType;
    private BigDecimal total;

    public CategoryTotalResponse() {
    }

    public CategoryTotalResponse(Long categoryId, String categoryName, CategoryType categoryType, BigDecimal total) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.total = total;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
