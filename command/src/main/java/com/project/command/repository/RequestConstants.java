package com.project.command.repository;

import java.util.List;

public class RequestConstants {

    public final static List<String> productType = List.of("DEBIT", "CREDIT", "INVEST", "SAVING");
    public final static List<String> requestType = List.of("USER_OF","ACTIVE_USER_OF","TRANSACTION_SUM_COMPARE",
                                                            "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
    public final static List<String> transactionType = List.of("WITHDRAW","DEPOSIT");
    public final static List<String> operationType = List.of(">","<","=",">=","<=");
    public static int positiveNumber;


}