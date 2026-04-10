package com.viniciusstorch.bookwhere_api.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "work_authors", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"work_id", "author_id"})
})
@Getter
@Setter
public class WorkAuthor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;
    
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
