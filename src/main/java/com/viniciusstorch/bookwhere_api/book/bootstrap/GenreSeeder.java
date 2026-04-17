package com.viniciusstorch.bookwhere_api.book.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.viniciusstorch.bookwhere_api.book.model.Genre;
import com.viniciusstorch.bookwhere_api.book.repository.GenreRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class GenreSeeder implements CommandLineRunner {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void run(String... args) {
List.of(
    new String[]{"fiction", "Fiction"},
    new String[]{"fantasy", "Fantasy"},
    new String[]{"science-fiction", "Science Fiction"},
    new String[]{"dystopian", "Dystopian"},
    new String[]{"adventure", "Adventure"},
    new String[]{"romance", "Romance"},
    new String[]{"historical-fiction", "Historical Fiction"},
    new String[]{"mystery", "Mystery"},
    new String[]{"thriller", "Thriller"},
    new String[]{"horror", "Horror"},
    new String[]{"crime", "Crime"},
    new String[]{"young-adult", "Young Adult"},
    new String[]{"children", "Children's"},
    new String[]{"graphic-novel", "Graphic Novel"},
    new String[]{"poetry", "Poetry"},
    new String[]{"drama", "Drama"},

    new String[]{"non-fiction", "Non-Fiction"},
    new String[]{"biography", "Biography"},
    new String[]{"autobiography", "Autobiography"},
    new String[]{"memoir", "Memoir"},
    new String[]{"history", "History"},
    new String[]{"philosophy", "Philosophy"},
    new String[]{"science", "Science"},
    new String[]{"self-help", "Self-Help"},
    new String[]{"business", "Business"},
    new String[]{"psychology", "Psychology"},
    new String[]{"travel", "Travel"},
    new String[]{"religion", "Religion"},
    new String[]{"true-crime", "True Crime"}
)
.forEach(g -> seedIfNotExists(g[0], g[1]));
    }
    
    private void seedIfNotExists(String code, String name) {
        if (!genreRepository.existsByCode(code))
            genreRepository.save(new Genre(code, name));
    }
}
