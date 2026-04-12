package com.viniciusstorch.bookwhere_api.book.model;

import java.util.Objects;

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
@Table(name = "book_genres", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"work_id", "genre_id"})
})
@Getter
@Setter
public class WorkGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof WorkGenre that))
            return false;

        if (!hasResolvedIds() || !that.hasResolvedIds())
            return false;

        return hasSameWork(that) && hasSameGenre(that);
    }

    @Override
    public int hashCode() {
        if (!hasResolvedIds())
            return System.identityHashCode(this);

        return Objects.hash(resolveWorkId(), resolveGenreId());
    }


    private boolean hasResolvedIds() {
        return resolveWorkId() != null && resolveGenreId() != null;
    }

    private boolean hasSameWork(WorkGenre other) {
        return Objects.equals(resolveWorkId(), other.resolveWorkId());
    }

    private boolean hasSameGenre(WorkGenre other) {
        return Objects.equals(resolveGenreId(), other.resolveGenreId());
    }

    private Long resolveWorkId() {
        return work != null ? work.getId() : null;
    }

    private Long resolveGenreId() {
        return genre != null ? genre.getId() : null;
    }
}
