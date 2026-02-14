package io.hexlet.spring;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.hexlet.spring.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        var filteredUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return ResponseEntity.of(filteredUser);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        var id = users.size() + 1;
        user.setId(id);
        users.add(user);
        return ResponseEntity.created(URI.create("api/posts")).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User data) {
        var maybeUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        var status = NOT_FOUND;
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            status = OK;
        }
        return ResponseEntity.status(status).body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        users.removeIf(user -> user.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
