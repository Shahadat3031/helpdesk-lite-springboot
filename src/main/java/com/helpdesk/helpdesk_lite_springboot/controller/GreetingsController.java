package com.helpdesk.helpdesk_lite_springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class GreetingsController {

    // Static In-Memory Data
    private static final List<User> users = new ArrayList<>();

    private static final AtomicInteger idCounter = new AtomicInteger(2);

    // Initial Users
    static {
        users.add(new User(1, "Shahadat", "shahadat@gmail.com", "01711111111"));
        users.add(new User(2, "Rahim", "rahim@gmail.com", "01822222222"));
    }

    // Existing API
    @GetMapping("/")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/requestMessage")
    public String requestMessage(@RequestParam String param) {
        return "Your message is: " + param;
    }

    // GET: Retrieve All Users
    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return users;
    }

    // GET: Retrieve User By ID
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {

        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create New User
    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {

        user.setId(idCounter.incrementAndGet());

        users.add(user);

        return user;
    }

    // PUT: Update Complete User
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @RequestBody User updatedUser) {

        for (User user : users) {

            if (user.getId().equals(id)) {

                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());

                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // PATCH: Update Only User Name
    @PatchMapping("/api/users/{id}")
    public ResponseEntity<User> updateUserName(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {

        for (User user : users) {

            if (user.getId().equals(id)) {

                if (request.containsKey("name")) {
                    user.setName(request.get("name"));
                }

                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Delete User
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {

        boolean removed = users.removeIf(
                user -> user.getId().equals(id)
        );

        if (removed) {
            return ResponseEntity.ok("User deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    // User Model (Static Inner Class)
    public static class User {

        private Integer id;
        private String name;
        private String email;
        private String phone;

        public User() {
        }

        public User(Integer id, String name, String email, String phone) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
