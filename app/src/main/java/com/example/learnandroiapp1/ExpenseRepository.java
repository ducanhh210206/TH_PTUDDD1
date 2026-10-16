package com.example.learnandroiapp1;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private static ExpenseRepository instance;
    private List<Expense> expenseList;

    private ExpenseRepository() {
        expenseList = new ArrayList<>();
        // Add sample data requested:
        // Ăn sáng | 30.000 | Ăn uống | 12/10/2026
        // Đổ xăng | 50.000 | Đi lại | 12/10/2026
        // Mua sách | 120.000 | Học tập | 13/10/2026
        expenseList.add(new Expense("1", "Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Phở bò buổi sáng"));
        expenseList.add(new Expense("2", "Đổ xăng", 50000, "Đi lại", "12/10/2026", "Đổ xăng xe máy"));
        expenseList.add(new Expense("3", "Mua sách", 120000, "Học tập", "13/10/2026", "Sách lập trình Android"));
    }

    public static synchronized ExpenseRepository getInstance() {
        if (instance == null) {
            instance = new ExpenseRepository();
        }
        return instance;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void addExpense(Expense expense) {
        expenseList.add(0, expense);
    }

    public void deleteExpense(String id) {
        expenseList.removeIf(e -> e.getId().equals(id));
    }

    public double getTotalAmount() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        return total;
    }
}
