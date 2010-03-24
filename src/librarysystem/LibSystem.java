/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem;

import librarysystem.controller.ActualItem;
import librarysystem.controller.Member;
import librarysystem.controller.Loan;
import librarysystem.controller.ReturnedLoan;
import librarysystem.controller.Reservation;
import librarysystem.controller.LibraryItem;
import librarysystem.controller.Book;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import librarysystem.controller.Author;

/**
 *
 * @author sim
 */
 public class LibSystem implements ILibrarySystem {
    private static LibSystem libSystem;

    private HashMap<Long, ActualItem> allActualItems;
    private HashMap<Long, LibraryItem> allItems;
    private HashMap<Long, Loan> allLoans;
    private HashMap<Long, Member> allMembers;
    private List<Reservation> allReservations;
    private HashMap<Long, ReturnedLoan> allReturnedLoans;

    private LibSystem() {
        allActualItems = new HashMap<Long, ActualItem>();
        allItems = new HashMap<Long, LibraryItem>();
        allLoans = new HashMap<Long, Loan>();
        allMembers = new HashMap<Long, Member>();
        allReservations = new ArrayList<Reservation>();
        allReturnedLoans = new HashMap<Long, ReturnedLoan>();
    }

    public static LibSystem getInstance() {
        if(libSystem == null) {
            libSystem = new LibSystem();
        }
        return libSystem;
    }

    //GET ALL
    public HashMap<Long, ActualItem> getAllActualItems() { return allActualItems; }
    public HashMap<Long, LibraryItem> getAllItems() { return allItems; }
    public HashMap<Long, Loan> getAllLoans() { return allLoans; }
    public HashMap<Long, Member> getAllMembers() { return allMembers; }
    public List<Reservation> getAllReservations() { return allReservations; }
    public HashMap<Long, ReturnedLoan> getAllReturnedLoans() { return allReturnedLoans; }

    public void serialize(LibSystem lib) {
        try {
            FileOutputStream fos = new FileOutputStream("LibSystem.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lib);
            oos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deserialize() {
        try {
            FileInputStream fis = new FileInputStream("LibSystem.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            LibSystem lib = (LibSystem) ois.readObject();
            ois.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //SCREEN: MEMBERS
    public Member getMember(long memberId) {
        return allMembers.get(memberId);
    }
    public Member createMember(String fname, String lname, String address, String telNo) {
        Member m = new Member(fname, lname, address, telNo);
        allMembers.put(m.getMemberId(), m);
        return m;
    }
    public HashMap<Long, Loan> getMemberLoans(long memberId) {
        Member m = allMembers.get(memberId);
        return m.getLoansWithKey();       
    }
    public HashMap<Long, ReturnedLoan> getMemberReturnedLoans(long memberId) {
        Member m = allMembers.get(memberId);
        return m.getLoanHistoryWithKey();
    }

    //SCREEN: ITEMS/COPIES/LOANS    
    public LibraryItem getItem(long itemId) {
        return allItems.get(itemId);
    }
    public Book createBook(String title, int maxCheckoutLength, String isbnA, String isbnB, int version, Date published) {
        Book b = new Book(title, maxCheckoutLength, isbnA, isbnB, version, published);
        allItems.put(b.getItemId(), b);
        return b;
    }
    public Author createAuthor(String fname, String lname) {
        return new Author(fname, lname);
    }
    public void addBookAuthor(Book book, Author author) {
        book.addAuthor(author);
    }  
    public Magazine createMagazine(String title, int maxCheckoutLength, int volume, int issueNo, Date published);
    public HashMap<Long, ActualItem> createCopies(long itemId, int numCopies);
    public Loan lendCopy(long memberId, long copyId);
    public ReturnedLoan returnCopy(long copyId);
    public Reservation reserveItem(long copyId, long memberId);

    //SCREEN: RESERVATIONS
    public List<Reservation> getAllReservations();

    //SCREEN: OVERDUE
    public HashMap<Long, Loan> getAllOverdue();
}
