package com.example.expense_tracker.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;



    @Data
    @AllArgsConstructor
    public class TransactionSummaryDTO {
        private BigDecimal totalIncome;
        private BigDecimal totalExpense;
        private BigDecimal netBalance;
        private Map<String, BigDecimal> monthlySummary; // e.g. "2024-05" => 5000


}
