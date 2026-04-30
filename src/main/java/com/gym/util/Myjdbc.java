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

            // 🔥 HARD CHECK (this is where your bug is hiding)
            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException("❌ DB ENV VARIABLES NOT SET");
            }

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUser);
            config.setPassword(dbPass);

            // 🔥 IMPORTANT for Docker/Render
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            config.setMaximumPoolSize(5);

            ds = new HikariDataSource(config);

            System.out.println("✅ Hikari initialized");

        } catch (Exception e) {
            System.out.println("❌ Hikari INIT FAILED:");
            e.printStackTrace();
        }
    }

    public static Connection myconn() {
        try {
            if (ds == null) {
                throw new RuntimeException("❌ DataSource is NULL (init failed)");
            }
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
