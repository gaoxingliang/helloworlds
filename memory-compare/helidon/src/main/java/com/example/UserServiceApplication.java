package com.example;

import io.helidon.webserver.WebServer;
import io.helidon.webserver.Routing;
import io.helidon.dbclient.DbClient;
import io.helidon.config.Config;

public class UserServiceApplication {
    public static void main(String[] args) {
        Config config = Config.create();
        
        // Create database client
        DbClient dbClient = DbClient.builder(config.get("db"))
                .build();
        
        // Initialize database table
        initializeDatabase(dbClient);
        
        // Create routing
        Routing routing = UserResource.createRouting(dbClient);
        
        // Create server
        WebServer.builder()
                .config(config.get("server"))
                .routing(routing)
                .build()
                .start()
                .thenAccept(server -> {
                    System.out.println("User Service started on http://localhost:" + server.port());
                })
                .exceptionally(throwable -> {
                    System.err.println("Failed to start server: " + throwable.getMessage());
                    return null;
                });
    }
    
    private static void initializeDatabase(DbClient dbClient) {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                phone VARCHAR(50)
            )
            """;
        
        dbClient.execute(exec -> exec.update(createTableSql))
                .thenAccept(result -> System.out.println("Database initialized successfully"))
                .exceptionally(throwable -> {
                    System.err.println("Failed to initialize database: " + throwable.getMessage());
                    return null;
                });
    }
}
