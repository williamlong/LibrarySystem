/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem;

import librarysystem.controller.ActualItem;
import librarysystem.controller.Author;
import librarysystem.controller.Member;
import librarysystem.controller.Book;
import librarysystem.controller.Magazine;
import librarysystem.controller.Loan;
import librarysystem.controller.ReturnedLoan;
import librarysystem.controller.Reservation;
import librarysystem.controller.LibraryItem;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

/**
 *
 * @author sgamboa
 */
public interface ILibrarySystem {
    /*
    public Member searchMember(long memberId);
    public long addMember(String fname, String lname, String phone, String address);
    public boolean removeMember(int memberId);
    public Hashtable<Integer, Member> getMembers();
    public void addAuthor(String fname, String lname, String phone, String address, 
            String credentials, Sting shortBio);
    public Author searchAuthor(String fname, String lname);
    public boolean removeAuthor(String fname, String lname);
    public void addBook(String title, String isbn) throws ApplicationException;
    public boolean removeBook(String isbn);
    public Book searchBook(String isbn);
    public long addBookCopy(String isbn);
    public void addMagazine(String title, String issueNo, int checkoutLength) throws ApplicationException;
    public void addMagazine(String tile, String issueNo);
    public boolean removeMagazine(String title, String issueNo);
    public Magazine searchMagazine(String title, String issueNo);
    public long addMagazineCopy(String title, String issueNo);
    public boolean loanBook(long memberId, String isbn);
    public boolean reserveBook(long memberId, String isbn);
    public boolean loanMagazine(long memberId, String title, String issueNo);
    public boolean reserveMagazine(long memberId, String title, String issueNo);
    public boolean returnCopy(long memberId, Copy copy);
    public boolean pickupReservation(Reservation res);
    public boolean cancelReservation(long memberId, Reservation res);
    public boolean mapAuthorToBook(String fname, String lname, String isbn);
     */

     /* Some more notes: PLAN
     *
     * I. Members Screen
     *
     * 1. search member
     * 2. show member
     * 3. edit member
     * ok 4. add member
     * ok 5. show member loans
     * ok 6. show member reservations
     * ok 7. show member history
     * 8. show overdues
     *
     * II. Item Screen
     *
     * 1. search item
     * 2. show item
     * 3. edit item
     * ok 4. add item
     * ok 5. add copy
     * 6. show book/mag borrowing members
     * 7. show copy borrowing member
     * 8. show copy history
     * ok 9. checkout copy
     * ok 10. return copy
     * ok 11. reserve book/mag
     * 12. reserve copy book/mag
     * 13. create author
     * ok 14. add author
     *
     * III. Reservation Screen (next)
     * IV. Overdue Screen (next)
     *
     * NOTE: We did not emphasis deletion or archiving of elements at this point.
    */

    //SCREEN: MEMBERS
    public HashMap<Long, Member> getAllMembers();
    public Member getMember(long memberId);
    public Member createMember(String fname, String lname, String address, String telNo);
    public HashMap<Long, Loan> getMemberLoans(long memberId);
    public HashMap<Long, ReturnedLoan> getMemberReturnedLoans(long memberId);

    //SCREEN: ITEMS/COPIES/LOANS
    public HashMap<Long, LibraryItem> getAllItems();
    public LibraryItem getItem(long itemId);
    public Book createBook(String title, int maxCheckoutLength, String isbnA, String isbnB, int version, Date published);
    public Author createAuthor(String fname, String lname);
    public void addBookAuthor(Book book, Author author);
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
