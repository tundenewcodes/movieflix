package com.movieflix.movieflix.dto;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {


    private Integer movieId;

    @NotBlank(message = "Please provide a title")
    private String title;

    @NotBlank(message = "Please provide a director")
    private String director;

   
    @NotBlank(message = "Please provide a studio")
    private String studio;


    private Set<String> movieCasts;

   
    private Integer releaseYear;

   
    @NotBlank(message = "Please provide a poster")
    private String poster;

    
    @NotBlank(message = "Please provide a poster url")
    private String posterUrl;

    
}
