/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.vm;

import hr.algebra.model.Movie;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dnlbe
 */
public class MovieTableModel extends AbstractTableModel{
    
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Description", "Actor", "PicturePath", "Genre", "Director"};
    
    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return Movie.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getDescription();
            case 3:
                return movies.get(rowIndex).getActor();
            case 4:
                return movies.get(rowIndex).getPicturePath();
            case 5:
                return movies.get(rowIndex).getGenre();
            case 6:
                return movies.get(rowIndex).getDirector();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); 
    }   
}
