/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem.controller;

/**
 *
 * @author sim
 */
public class Author implements java.io.Serializable {

    private long authorId;
    private String fname;
    private String lname;

    public Author(String fname, String lname) {
        authorId = SimulatedIdGenerator.getInstance().generateId();
        this.fname = fname;
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
