package com.movieflix.movieflix.entities;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide a title")
    private String title;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide a director")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please provide a studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCasts;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide a poster")
    private String poster;

}
