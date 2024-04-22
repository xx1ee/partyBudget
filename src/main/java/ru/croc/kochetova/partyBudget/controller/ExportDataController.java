package ru.croc.kochetova.partyBudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ru.croc.kochetova.partyBudget.service.DataExportService;

@Controller
public class ExportDataController {
    private final DataExportService dataExportService;
    @Autowired
    public ExportDataController(DataExportService dataExportService) {
        this.dataExportService = dataExportService;
    }
    @PostMapping("/exportExpenses")
    public String handleExportExpenses() {
        dataExportService.exportExpenses();
        return "home_admin";
    }
    @PostMapping("/exportIncomes")
    public String handleExportIncomes() {
        dataExportService.exportIncomes();
        return "home_admin";
    }
}
