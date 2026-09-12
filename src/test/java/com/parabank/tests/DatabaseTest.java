package com.parabank.tests;

import com.parabank.utils.DatabaseConnection;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    @Test
    public void testAccountBalance_DB01() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT balance FROM account WHERE id = 12345";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Assert.assertTrue(rs.next(), "No account found with id 12345");

            BigDecimal balance = rs.getBigDecimal("balance");
            Assert.assertEquals(balance, new BigDecimal("1000.00"),
                    "Checking account balance did not match expected seed value");
        }
    }

    @Test
    public void testTransactionLinkedToAccount_DB02() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT t.amount, t.type, t.description " +
                "FROM transaction t " +
                "JOIN account a ON t.account_id = a.id " +
                "WHERE a.id = 12345";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Assert.assertTrue(rs.next(), "No transaction found for account 12345");

            BigDecimal amount = rs.getBigDecimal("amount");
            String type = rs.getString("type");
            String description = rs.getString("description");

            Assert.assertEquals(amount, new BigDecimal("50.00"), "Transaction amount mismatch");
            Assert.assertEquals(type, "Debit", "Transaction type mismatch");
            Assert.assertEquals(description, "Transfer to Savings", "Transaction description mismatch");
        }
    }

    @Test
    public void testBalanceConsistencyAcrossAccounts_DB03() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT SUM(balance) as total FROM account WHERE customer_id = 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Assert.assertTrue(rs.next(), "No accounts found for customer 1");

            BigDecimal total = rs.getBigDecimal("total");
            // Checking (1000.00) + Savings (500.00) = 1500.00
            Assert.assertEquals(total, new BigDecimal("1500.00"),
                    "Total balance across customer's accounts did not match expected sum");
        }
    }
    
    @AfterClass
    public void tearDown() {
        DatabaseConnection.closeConnection();
    }
}