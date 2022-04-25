package me.shenderov.casino.enums;

public enum TransactionStatus {
    NEW("New"),
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    OUT_OF_BALANCE("Out of Balance"),
    PROCESSED("Processed");

    public final String status;

    TransactionStatus(String status) {
        this.status = status;
    }
}
