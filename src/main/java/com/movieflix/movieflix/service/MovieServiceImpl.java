package com.movieflix.movieflix.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieflix.movieflix.dto.MovieDto;
import com.movieflix.movieflix.dto.MoviePageResponse;
import com.movieflix.movieflix.entities.Movie;
import com.movieflix.movieflix.exceptions.FileExitException;
import com.movieflix.movieflix.exceptions.MovieNotFoundException;
import com.movieflix.movieflix.repositories.MovieRepository;

@Service // Add this annotation
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExitException("File exists already");
        }
        String uploadedFileName = fileService.uploadFile(path, file);

        movieDto.setPoster(uploadedFileName);

        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCasts(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());

        Movie savedMovie = movieRepository.save(movie);

        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        MovieDto response = new MovieDto(

                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCasts(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(), posterUrl);

        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        MovieDto response = new MovieDto(

                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCasts(),
                movie.getReleaseYear(),
                movie.getPoster(), posterUrl);
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(

                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCasts(),
                    movie.getReleaseYear(),
                    movie.getPoster(), posterUrl);
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // TODO Auto-generated method stub

        Movie mv = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        String filename = mv.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + filename));
            filename = fileService.uploadFile(path, file);
        }
        movieDto.setPoster(filename);

        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCasts(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());
        Movie movieUpdated = movieRepository.save(movie);
        String posterUrl = baseUrl + "/file/" + filename;
        MovieDto response = new MovieDto(

                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCasts(),
                movie.getReleaseYear(),
                movie.getPoster(), posterUrl

        );
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // TODO Auto-generated method stub
        Movie mV = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        Integer id = mV.getMovieId();
        Files.deleteIfExists(Paths.get(path + File.separator + mV.getPoster()));
        movieRepository.delete(mV);
        return "Movie deleted with id: " + id;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();
        List<MovieDto> movieDtos = new ArrayList<>();

        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(

                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCasts(),
                    movie.getReleaseYear(),
                    movie.getPoster(), posterUrl);
            movieDtos.add(movieDto);
        }
        return new MoviePageResponse(movieDtos, pageNumber, pageSize, moviePages.getTotalElements(),
                moviePages.getTotalPages(), moviePages.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy,
            String dir) {

        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();
        List<MovieDto> movieDtos = new ArrayList<>();

        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(

                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCasts(),
                    movie.getReleaseYear(),
                    movie.getPoster(), posterUrl);
            movieDtos.add(movieDto);
        }
                return new MoviePageResponse(movieDtos, pageNumber, pageSize, moviePages.getTotalElements(),
                moviePages.getTotalPages(), moviePages.isLast());

    }
}
