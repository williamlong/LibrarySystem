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
        private String isbnA;
        private String isbnB;
        private int version;
        private Date published;
        private List<Author> authors;

    public Book(String title, int maxCheckoutLength, String isbnA, String isbnB,
            int version, Date published) {
        super(title, maxCheckoutLength);
        this.isbnA = isbnA;
        this.isbnB = isbnB;
        this.version = version;
        this.published = published;
        authors = new ArrayList<Author>();
    }

    public void reserve() {
        //TODO: code
    }
    public void getBookInfo() {
        //TODO: code
    }
    public void addAuthor(Author author) {
        authors.add(author);
    }
}
