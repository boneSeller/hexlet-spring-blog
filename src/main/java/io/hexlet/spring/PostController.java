package io.hexlet.spring;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.hexlet.spring.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer id) {
        var filteredPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();
        if (filteredPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(filteredPost);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        var id = posts.size() + 1;
        post.setId(id);
        posts.add(post);
        return ResponseEntity.created(URI.create("/posts")).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(@PathVariable Integer id, @RequestBody Post data) {
        var maybePost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setAuthor(data.getAuthor());
            post.setTitle(data.getTitle());
            post.setContent(data.getContent());
            post.setCreatedAt(data.getCreatedAt());
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        posts.removeIf(post -> post.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
