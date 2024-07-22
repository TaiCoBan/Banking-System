package me.project.bankingsystem.dto;

public class DepositRequest {
    private Long amount;
    private String content;

    // Getters và Setters
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
