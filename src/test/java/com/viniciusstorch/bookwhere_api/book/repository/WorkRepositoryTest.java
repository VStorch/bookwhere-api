package com.viniciusstorch.bookwhere_api.book.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.viniciusstorch.bookwhere_api.book.model.Author;
import com.viniciusstorch.bookwhere_api.book.model.Work;
import com.viniciusstorch.bookwhere_api.book.model.WorkAuthor;

@SpringBootTest
public class WorkRepositoryTest {
    
    @Autowired
    private WorkRepository workRepository;
    
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldSaveWorkWithAuthor() {

        Author author = new Author();
        author.setName("Franz Kafka");
        author = authorRepository.save(author);

        Work work = new Work();
        work.setTitle("The Metamorphosis");
        work.setSynopsis("A man wakes up one morning to find himself transformed into a giant insect.");

        WorkAuthor workAuthor = new WorkAuthor();
        workAuthor.setWork(work);
        workAuthor.setAuthor(author);

        work.getWorkAuthors().add(workAuthor);
        author.getWorkAuthor().add(workAuthor);

        Work saved = workRepository.save(work);

        assertNotNull(saved.getId());
        assertEquals(1, saved.getWorkAuthors().size());
    }

    @Test
    void shouldNotAllowDuplicateWorkAuthor() {

        Author author = new Author();
        author.setName("Author");
        author = authorRepository.save(author);

        Work work = new Work();
        work.setTitle("Book");

        WorkAuthor wa1 = new WorkAuthor();
        wa1.setWork(work);
        wa1.setAuthor(author);

        WorkAuthor wa2 = new WorkAuthor();
        wa2.setWork(work);
        wa2.setAuthor(author);

        work.getWorkAuthors().add(wa1);
        work.getWorkAuthors().add(wa2);

        assertThrows(DataIntegrityViolationException.class, () -> {
            workRepository.saveAndFlush(work);
        });
    }
}
