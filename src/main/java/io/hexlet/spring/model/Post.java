package io.hexlet.spring.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Post {

    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
