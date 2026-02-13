package io.hexlet.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.hexlet.spring.model.Post;
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
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable Integer id) {
        return posts.stream()
                    .filter(post -> post.getId().equals(id))
                    .findFirst();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        var id = posts.size() + 1;
        post.setId(id);
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable Integer id, @RequestBody Post data) {
        var maybePost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setAuthor(data.getAuthor());
            post.setTitle(data.getTitle());
            post.setContent(data.getContent());
            post.setCreatedAt(data.getCreatedAt());
        }
        return data;
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable Integer id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
