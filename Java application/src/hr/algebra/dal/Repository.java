/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author dnlbe
 */
public interface Repository {
    
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception; 
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    void deleteMovies() throws Exception;
    
    void AdminLogin() throws Exception;
    
    int createActor(Actor actor) throws Exception;
    List<Actor> selectActors() throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    void deleteActor(int id) throws Exception;
    void updateActor(int id, Actor data) throws Exception;
    
    int createDirector(Director director) throws Exception;
    List<Director> selectDirectors() throws Exception;
    Optional<Director> selectDirector(int id) throws Exception;
    void deleteDirector(int id) throws Exception;
    void updateDirector(int id, Director data) throws Exception;
    
    List<Actor> GetActorForMovieById (int movieId) throws Exception;
    void ActorToMovie(int movieId, int glumacId) throws Exception;
    void DeleteActorFromMovie(int movieId, int glumacId) throws Exception;
    
    List<Director> GetDirectorForMovieById (int movieId) throws Exception;
    void DirectorToMovie(int movieId, int redateljId) throws Exception;
    void DeleteDirectorFromMovie(int movieId, int redateljId) throws Exception;
    
    void CreateJavaUser(String username, String pass) throws Exception;
    Optional AuthUser(String username, String pass) throws Exception;
}
