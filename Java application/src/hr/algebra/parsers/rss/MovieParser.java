/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.utils.FileUtils;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Movie;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author dnlbe
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static List<Actor> ParseActors(String poslaniString) {
        List<Actor> actors = new ArrayList();
        
        if (poslaniString.length() == 0) {
            return actors;
        }
        
        String[] parsedActor;
        parsedActor = poslaniString.split(",", -1);
        
        for (int i = 0; i < parsedActor.length; i++) {
            String[] singleActor = parsedActor[i].trim().split(" ", -1);
            Actor actor = new Actor(singleActor[0].trim(), singleActor[1].trim());
            actors.add(actor);
        }
        
        
        return actors;
    }; 

    private MovieParser() {
    }

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case ZANR:
                                    if (!data.isEmpty()) {
                                        movie.setGenre(data);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (!data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;
                                case REDATELJ:
                                if (!data.isEmpty()) {
                                    movie.setDirector(data);
                                }
                                break;
                                case GLUMCI:
                                
                                    movie.setActors(ParseActors(data));
                                    movie.setActor(data);
                                break;
                                case PLAKAT:
                                // bugfix -> prevent to enter 2 times!!!
                                if (startElement != null && movie.getPicturePath() == null) {
                                    //Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                    if (!data.isEmpty()) {
                                        handlePicture(movie, data);
                                    }
                                }
                                
                                //case PUB_DATE:
                                //if (!data.isEmpty()) {
                                    //LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                    //7movie.setPublishedDate(publishedDate);
                                //}
                                //break;
                               
                            }
                        }
                        break;
                }
            }
        }
        return movies;

    }

    private static void handlePicture(Movie movie, String pictureUrl) {

        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            movie.setPicturePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {

        ITEM("item"),
        TITLE("title"),
        REDATELJ("redatelj"),
        DESCRIPTION("description"),
        GLUMCI("glumci"),
        PLAKAT("plakat"),
        ZANR("zanr");
       

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

}
