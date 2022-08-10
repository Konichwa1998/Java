/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author dnlbe
 */
public class SqlRepository implements Repository {

    private static final String ID = "Id";
    private static final String NAZIV = "Naziv";
    private static final String OPISFILMA = "OpisFilma";
    private static final String REDATELJ = "Redatelj";
    private static final String GLUMCI = "Glumci";
    private static final String ZANR = "Zanr";
    private static final String PICTURE_PATH = "PicturePath";
    
    private static final String ADMIN_USERNAME = "Username";
    private static final String ADMIN_PASSWORD = "Pass";
    
    private static final String ID_ACTOR = "Id";
    private static final String IME = "Ime";
    private static final String PREZIME = "Prezime";
    
    
    private static final String ID_MOVIE = "MovieId";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String DELETE_MOVIES = "{ CALL deleteAllMovies }";
    
    private static final String ADMIN_LOGIN = "{ CALL AdminLogin}";
    
    private static final String CREATE_ACTOR = "{ CALL createMovie (?,?,?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?,?) }";
    
    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?) }";
    
    private static final String GET_ACTOR_BY_ID = "{ CALL GetActorForMovieById (?) }";
    private static final String ACTOR_TO_MOVIE = "{ CALL ActorToMovie (?,?) }";
    private static final String DELETE_ACTOR_FROM_MOVIE = "{ CALL DeleteActorFromMovie (?,?) }";
    
    private static final String GET_DIRECTOR_BY_ID = "{ CALL GetDirectorForMovieById (?) }";
    private static final String DIRECTOR_TO_MOVIE = "{ CALL DirectorToMovie (?,?) }";
    private static final String DELETE_DIRECTOR_FROM_MOVIE = "{ CALL DeleteDirectorFromMovie (?,?) }";
    
    private static final String CREATE_JAVA_USER = "{ CALL CreateJavaUser (?,?) }";
    private static final String AUTH_JAVA_USER = "{ CALL AuthUser (?,?) }";
    
     
     

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            stmt.setString("@" + NAZIV, movie.getTitle());
            stmt.setString("@" + OPISFILMA, movie.getDescription());
            stmt.setString("@" + REDATELJ, movie.getDirector());
            stmt.setString("@" + ZANR, movie.getGenre());
            stmt.setString("@" + PICTURE_PATH, movie.getPicturePath());
            stmt.setString("@" + GLUMCI, movie.getActor());
            stmt.registerOutParameter("@" + ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt("@" + ID);
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString("@" + NAZIV, data.getTitle());
            stmt.setString("@" + OPISFILMA, data.getDescription());
            stmt.setString("@" + REDATELJ, data.getDirector());
            stmt.setString("@" + PICTURE_PATH, data.getPicturePath());
            stmt.setString("@" + ZANR, data.getGenre());
            stmt.setString("@" + GLUMCI, data.getActor());
            stmt.setInt("@" + ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt("@" + ID, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt("@" + ID, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                    
                    rs.getInt(ID),
                            rs.getString(NAZIV),
                            rs.getString(ZANR),
                            rs.getString(OPISFILMA),
                            rs.getString(GLUMCI),
                            rs.getString(REDATELJ),
                            rs.getString(PICTURE_PATH))
                    );
                            
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID),
                        rs.getString(NAZIV),
                        rs.getString(ZANR),
                        rs.getString(OPISFILMA),
                        rs.getString(GLUMCI),
                        rs.getString(REDATELJ),
                        rs.getString(PICTURE_PATH)));
            }
        }
        return movies;
    }


    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString("@" + NAZIV, movie.getTitle());
                stmt.setString("@" + OPISFILMA, movie.getDescription());
                stmt.setString("@" + REDATELJ, movie.getDirector());
                stmt.setString("@" + ZANR, movie.getGenre());
                stmt.setString("@" + PICTURE_PATH, movie.getPicturePath());
                stmt.setString("@" + GLUMCI, movie.getActor());
                stmt.registerOutParameter("@" + ID, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void deleteMovies() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DELETE_MOVIES)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void AdminLogin() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(ADMIN_LOGIN)) {
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            String user = rs.getString(ADMIN_USERNAME);
            String pass = rs.getString(ADMIN_PASSWORD);
        }
    }

    @Override
    public int createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            stmt.setString("@" + IME, actor.getName());
            stmt.setString("@" + PREZIME, actor.getSurname());
   
            stmt.registerOutParameter("@" + ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt("@" + ID);
        }
    }

    @Override
    public List<Actor> selectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_ACTORS);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID),
                        rs.getString(IME),
                        rs.getString(PREZIME)));
            }
        }
        return actors;
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {
            stmt.setInt("@" + ID, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                    
                    rs.getInt(ID),
                            rs.getString(IME),
                            rs.getString(PREZIME))
                    );
                            
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            stmt.setString("@" + IME, director.getName());
            stmt.setString("@" + PREZIME, director.getSurname());
   
            stmt.registerOutParameter("@" + ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt("@" + ID);
        }
    }

    @Override
    public List<Director> selectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID),
                        rs.getString(IME),
                        rs.getString(PREZIME)));
            }
        }
        return directors;
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {
            stmt.setInt("@" + ID, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                    
                    rs.getInt(ID),
                            rs.getString(IME),
                            rs.getString(PREZIME))
                    );
                            
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt("@" + ID, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateDirector(int id, Director data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setString("@" + IME, data.getName());
            stmt.setString("@" + PREZIME, data.getSurname());
            stmt.setInt("@" + ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt("@" + ID, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateActor(int id, Actor data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setString("@" + IME, data.getName());
            stmt.setString("@" + PREZIME, data.getSurname());
            stmt.setInt("@" + ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Actor> GetActorForMovieById(int movieId) throws Exception{
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ACTOR_BY_ID)) {
            stmt.setInt("@" + "movieId", movieId);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    actors.add(
                            new Actor(rs.getInt(ID), rs.getString(IME), 
                                    rs.getString(PREZIME))
                    );
                }
            }
            
            return actors;
        }
    }

    @Override
    public void ActorToMovie(int movieId, int glumacId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(ACTOR_TO_MOVIE)) {
            stmt.setInt("@" + "movieId", movieId );
            stmt.setInt("@" + "glumacId", glumacId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteActorFromMovie(int movieId, int glumacId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt =  con.prepareCall(DELETE_ACTOR_FROM_MOVIE)) {
            stmt.setInt("@" + "movieId", movieId);
            stmt.setInt("@" + "glumacId", glumacId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Director> GetDirectorForMovieById(int movieId) throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_DIRECTOR_BY_ID)) {
            stmt.setInt("@" + "movieId", movieId);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    directors.add(
                            new Director(rs.getInt(ID), rs.getString(IME), 
                                    rs.getString(PREZIME))
                    );
                }
            }
            
            return directors;
        }
    }

    @Override
    public void DirectorToMovie(int movieId, int redateljId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DIRECTOR_TO_MOVIE)) {
            stmt.setInt("@" + "movieId", movieId );
            stmt.setInt("@" + "redateljId", redateljId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteDirectorFromMovie(int movieId, int redateljId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt =  con.prepareCall(DELETE_DIRECTOR_FROM_MOVIE)) {
            stmt.setInt("@" + "movieId", movieId);
            stmt.setInt("@" + "redateljId", redateljId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void CreateJavaUser(String username, String pass) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt =  con.prepareCall(CREATE_JAVA_USER)) {
            stmt.setString("@" + "Username", username);
            stmt.setString("@" + "Pass", pass);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional AuthUser(String username, String pass) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(AUTH_JAVA_USER)) {
            stmt.setString("@" + "Username", username);
            stmt.setString("@" + "Pass", pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(true);
                            
                }
            }
        }
        return Optional.empty();
    }
}
       

    

   
