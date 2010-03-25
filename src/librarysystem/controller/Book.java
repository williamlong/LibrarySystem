/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sim
 */
public class Book extends LibraryItem implements java.io.Serializable {
    private String isbn;
    private int version;
    private Date published;
    private List<Author> authors;

    public Book(String title, int maxCheckoutLength, String isbn,
            int version, Date published) {
        super(title, maxCheckoutLength);
        this.isbn = isbn;
        this.version = version;
        this.published = published;
        authors = new ArrayList<Author>();
    }
    
    public void addAuthor(Author author) {
        authors.add(author);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public Date getPublished() {
        return published;
    }

    public int getVersion() {
        return version;
    }
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
