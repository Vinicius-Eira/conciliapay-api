package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.MonthlyChartDTO;
import com.conciliapay.api.core.entities.GatewayTransaction;
import com.conciliapay.api.core.repositories.GatewayTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetDashboardChartUseCase {

    private final GatewayTransactionRepository gatewayRepo;

    public GetDashboardChartUseCase(GatewayTransactionRepository gatewayRepo) {
        this.gatewayRepo = gatewayRepo;
    }

    public List<MonthlyChartDTO> execute(String merchantDocument) {

        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(5).withDayOfMonth(1).withHour(0).withMinute(0);
        List<GatewayTransaction> transactions = gatewayRepo.findByMerchantDocumentAndTransactionDateAfter(merchantDocument, sixMonthsAgo);

        Map<Integer, List<GatewayTransaction>> transactionsByMonth = transactions.stream()
                .collect(Collectors.groupingBy(tx -> tx.getTransactionDate().getMonthValue()));

        List<MonthlyChartDTO> chartData = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            LocalDateTime currentIteratedMonth = LocalDateTime.now().minusMonths(i);
            int monthNumber = currentIteratedMonth.getMonthValue();

            String monthName = currentIteratedMonth.getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));

            List<GatewayTransaction> monthTransactions = transactionsByMonth.getOrDefault(monthNumber, Collections.emptyList());

            BigDecimal revenue = BigDecimal.ZERO;
            BigDecimal expenses = BigDecimal.ZERO;

            for (GatewayTransaction tx : monthTransactions) {
                revenue = revenue.add(tx.getAmount());

                BigDecimal fee = tx.getAmount().subtract(tx.getNetAmount());
                expenses = expenses.add(fee);
            }

            String formattedMonthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

            chartData.add(new MonthlyChartDTO(formattedMonthName, revenue, expenses));
        }

        return chartData;
    }
}
