package com.movieflix.movieflix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieflix.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    
}
