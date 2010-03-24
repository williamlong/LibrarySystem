/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sim
 */
public class Author implements java.io.Serializable {
    private long authorId;
    private String fname;
    private String lname;
    //private List<Book> books;

    //public Author(String fname, String lname, Book book) {
    public Author(String fname, String lname) {
        authorId = SimulatedIdGenerator.getInstance().generateId();
        this.fname = fname;
        this.lname = lname;

        //books = new ArrayList<Book>();
        //books.add(book);
    }

//    public void addBook(Book book) {
//        books.add(book);
//    }
//    public List<Book> getBooks() {
//        return books;
//    }
    //NOTE: How about adding support for removing book in books.
}
