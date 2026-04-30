package com.gym.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;

public class Myjdbc {

    private static HikariDataSource ds;

    static {
        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASSWORD");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUser);
            config.setPassword(dbPass);
            config.setMaximumPoolSize(5);

            ds = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection myconn() {
        try {
            return ds.getConnection(); // 🔥 pooled connection
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
