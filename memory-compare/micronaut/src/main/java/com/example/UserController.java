package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@Controller("/api/users")
public class UserController {
    
    @Inject
    private UserRepository userRepository;
    
    @Get
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Get("/{id}")
    public HttpResponse<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(HttpResponse::ok)
                  .orElse(HttpResponse.notFound());
    }
    
    @Get("/email/{email}")
    public HttpResponse<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(HttpResponse::ok)
                  .orElse(HttpResponse.notFound());
    }
    
    @Post
    public HttpResponse<User> createUser(@Body User user) {
        User savedUser = userRepository.save(user);
        return HttpResponse.created(savedUser);
    }
    
    @Put("/{id}")
    public HttpResponse<User> updateUser(Long id, @Body User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            User updatedUser = userRepository.update(user);
            return HttpResponse.ok(updatedUser);
        }
        return HttpResponse.notFound();
    }
    
    @Delete("/{id}")
    public HttpResponse<Void> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
