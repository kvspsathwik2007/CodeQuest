
package com.codequest.util;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("Database Connected Successfully!");
                System.out.println("Database: " +
                    con.getCatalog());

                con.close();
            }

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }
}
