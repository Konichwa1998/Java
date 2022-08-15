/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;

/**
 *
 * @author Korsnik
 */
public class Movie {
    
    private int id;
    private String title;
    private String genre;
    private String description;
    private String actor;
    private String director;
    private String picturePath;
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(int id, String title, String genre, String description, String actor, String director, String picturePath) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.actor = actor;
        this.director = director;
        this.picturePath = picturePath;
    }
    
    

    public Movie(String title, String genre, String description, String actor, String director, String picturePath) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.actor = actor;
        this.director = director;
        this.picturePath = picturePath;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
    

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    @Override
    public String toString() {
        return id + " - " + title;
    }
    
}
