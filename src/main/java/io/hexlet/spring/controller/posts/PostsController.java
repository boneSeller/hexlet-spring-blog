package io.hexlet.spring.controller.posts;

import java.net.URI;
import java.util.List;

import io.hexlet.spring.model.Post;
import io.hexlet.spring.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(postRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        var post = postRepository.findById(id);
        if (post.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(post);
    }

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.created(URI.create("/posts")).body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post data) {
        var maybePost = postRepository.findById(id);
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setTitle(data.getTitle());
            post.setContent(data.getContent());
            post.setPublished(data.getPublished());
            postRepository.save(post);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
