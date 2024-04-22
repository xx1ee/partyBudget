package ru.croc.kochetova.partyBudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Expense;
import ru.croc.kochetova.partyBudget.model.Income;
import ru.croc.kochetova.partyBudget.repository.ExpenseRepository;
import ru.croc.kochetova.partyBudget.repository.IncomeRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class DataExportService {
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private static final String exportExpensesFile = "src/main/resources/export_files/expenses.csv";
    private static final String exportIncomesFile = "src/main/resources/export_files/incomes.csv";
    @Autowired
    public DataExportService(ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    public void exportExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        Path filePath = Path.of(exportExpensesFile);
        exportData(expenses, filePath);
    }
    public void exportIncomes() {
        List<Income> incomes = incomeRepository.findAll();
        Path filePath = Path.of(exportIncomesFile);
        exportData(incomes, filePath);
    }
    private <T> void exportData(List<T> dataList, Path filePath) {
        try {
            for (T data : dataList) {
                String valueString = data.toString();
                Files.write(filePath, (valueString + System.lineSeparator()).getBytes(),
                        Files.exists(filePath) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
