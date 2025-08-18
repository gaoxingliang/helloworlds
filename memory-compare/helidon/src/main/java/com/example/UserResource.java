package com.example;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import io.helidon.common.http.Http;
import io.helidon.common.http.MediaType;
import io.helidon.common.reactive.Single;

import java.util.List;
import java.util.stream.Collectors;

public class UserResource {
    private final DbClient dbClient;
    
    public UserResource(DbClient dbClient) {
        this.dbClient = dbClient;
    }
    
    public static Routing createRouting(DbClient dbClient) {
        UserResource resource = new UserResource(dbClient);
        return Routing.builder()
                .get("/api/users", resource::getAllUsers)
                .get("/api/users/{id}", resource::getUserById)
                .get("/api/users/email/{email}", resource::getUserByEmail)
                .post("/api/users", resource::createUser)
                .put("/api/users/{id}", resource::updateUser)
                .delete("/api/users/{id}", resource::deleteUser)
                .build();
    }
    
    private void getAllUsers(ServerRequest request, ServerResponse response) {
        dbClient.execute(exec -> exec.query("SELECT id, name, email, phone FROM users"))
                .collectList()
                .thenAccept(rows -> {
                    List<User> users = rows.stream()
                            .map(this::mapRowToUser)
                            .collect(Collectors.toList());
                    response.send(users);
                })
                .exceptionally(throwable -> {
                    response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                    response.send("Error retrieving users: " + throwable.getMessage());
                    return null;
                });
    }
    
    private void getUserById(ServerRequest request, ServerResponse response) {
        String idParam = request.path().param("id");
        try {
            Long id = Long.parseLong(idParam);
            dbClient.execute(exec -> exec.query("SELECT id, name, email, phone FROM users WHERE id = ?", id))
                    .collectList()
                    .thenAccept(rows -> {
                        if (rows.isEmpty()) {
                            response.status(Http.Status.NOT_FOUND_404);
                            response.send("User not found");
                        } else {
                            User user = mapRowToUser(rows.get(0));
                            response.send(user);
                        }
                    })
                    .exceptionally(throwable -> {
                        response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                        response.send("Error retrieving user: " + throwable.getMessage());
                        return null;
                    });
        } catch (NumberFormatException e) {
            response.status(Http.Status.BAD_REQUEST_400);
            response.send("Invalid user ID");
        }
    }
    
    private void getUserByEmail(ServerRequest request, ServerResponse response) {
        String email = request.path().param("email");
        dbClient.execute(exec -> exec.query("SELECT id, name, email, phone FROM users WHERE email = ?", email))
                .collectList()
                .thenAccept(rows -> {
                    if (rows.isEmpty()) {
                        response.status(Http.Status.NOT_FOUND_404);
                        response.send("User not found");
                    } else {
                        User user = mapRowToUser(rows.get(0));
                        response.send(user);
                    }
                })
                .exceptionally(throwable -> {
                    response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                    response.send("Error retrieving user: " + throwable.getMessage());
                    return null;
                });
    }
    
    private void createUser(ServerRequest request, ServerResponse response) {
        request.content().as(User.class)
                .thenAccept(user -> {
                    dbClient.execute(exec -> exec.insert("INSERT INTO users (name, email, phone) VALUES (?, ?, ?)",
                            user.getName(), user.getEmail(), user.getPhone()))
                            .thenAccept(result -> {
                                user.setId(result);
                                response.status(Http.Status.CREATED_201);
                                response.send(user);
                            })
                            .exceptionally(throwable -> {
                                response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                                response.send("Error creating user: " + throwable.getMessage());
                                return null;
                            });
                })
                .exceptionally(throwable -> {
                    response.status(Http.Status.BAD_REQUEST_400);
                    response.send("Invalid user data: " + throwable.getMessage());
                    return null;
                });
    }
    
    private void updateUser(ServerRequest request, ServerResponse response) {
        String idParam = request.path().param("id");
        try {
            Long id = Long.parseLong(idParam);
            request.content().as(User.class)
                    .thenAccept(userUpdate -> {
                        dbClient.execute(exec -> exec.update("UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?",
                                userUpdate.getName(), userUpdate.getEmail(), userUpdate.getPhone(), id))
                                .thenAccept(result -> {
                                    if (result > 0) {
                                        userUpdate.setId(id);
                                        response.send(userUpdate);
                                    } else {
                                        response.status(Http.Status.NOT_FOUND_404);
                                        response.send("User not found");
                                    }
                                })
                                .exceptionally(throwable -> {
                                    response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                                    response.send("Error updating user: " + throwable.getMessage());
                                    return null;
                                });
                    })
                    .exceptionally(throwable -> {
                        response.status(Http.Status.BAD_REQUEST_400);
                        response.send("Invalid user data: " + throwable.getMessage());
                        return null;
                    });
        } catch (NumberFormatException e) {
            response.status(Http.Status.BAD_REQUEST_400);
            response.send("Invalid user ID");
        }
    }
    
    private void deleteUser(ServerRequest request, ServerResponse response) {
        String idParam = request.path().param("id");
        try {
            Long id = Long.parseLong(idParam);
            dbClient.execute(exec -> exec.delete("DELETE FROM users WHERE id = ?", id))
                    .thenAccept(result -> {
                        if (result > 0) {
                            response.status(Http.Status.NO_CONTENT_204);
                            response.send();
                        } else {
                            response.status(Http.Status.NOT_FOUND_404);
                            response.send("User not found");
                        }
                    })
                    .exceptionally(throwable -> {
                        response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
                        response.send("Error deleting user: " + throwable.getMessage());
                        return null;
                    });
        } catch (NumberFormatException e) {
            response.status(Http.Status.BAD_REQUEST_400);
            response.send("Invalid user ID");
        }
    }
    
    private User mapRowToUser(DbRow row) {
        return new User(
                row.column("id").as(Long.class),
                row.column("name").as(String.class),
                row.column("email").as(String.class),
                row.column("phone").as(String.class)
        );
    }
}
