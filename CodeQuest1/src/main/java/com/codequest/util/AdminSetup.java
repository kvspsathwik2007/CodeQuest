
package com.codequest.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AdminSetup {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter admin username: ");
            String username = sc.nextLine().trim();

            System.out.print("Enter admin password: ");
            String password = sc.nextLine();

            if (username.isEmpty() || password.length() < 8) {
                System.out.println(
                    "Username required and password must be 8+ characters.");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {

                String checkSql =
                    "SELECT admin_id FROM admins WHERE username = ?";

                try (PreparedStatement check =
                        con.prepareStatement(checkSql)) {

                    check.setString(1, username);

                    try (ResultSet rs = check.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(
                                "Admin username already exists!");
                            return;
                        }
                    }
                }

                String sql =
                    "INSERT INTO admins (username, password) VALUES (?, ?)";

                try (PreparedStatement ps =
                        con.prepareStatement(sql)) {

                    ps.setString(1, username);
                    ps.setString(2,
                        PasswordUtil.hashPassword(password));

                    ps.executeUpdate();

                    System.out.println(
                        "Admin account created successfully!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
