package librarysystem;

import java.util.List;
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
import java.util.HashMap;

public interface ILibrarySystem {
    //SYSTEM Serialization
    public void serialize();
    public void deserialize();

    //SCREEN: MEMBERS
    public HashMap<Long, Member> getAllMembers();
    public Member getMember(long memberId);
    public Member createMember(String fname, String lname, String address, String telNo);
    public HashMap<Long, Loan> getMemberLoans(long memberId);
    public HashMap<Long, ReturnedLoan> getMemberReturnedLoans(long memberId);

    //SCREEN: ITEMS | COPIES | LOANS
    public HashMap<Long, LibraryItem> getAllItems();
    public LibraryItem getItem(long itemId);
    public HashMap<Long, ActualItem> getItemActualCopies(long itemId);
    public ActualItem getActualItem(long copyId);
    public Book createBook(String title, int maxCheckoutLength, int numCopies, String isbn, int version, Date published, List<Author> authors);
    public Author createAuthor(String fname, String lname);
    public Magazine createMagazine(String title, int maxCheckoutLength, int numCopies, int volume, int issueNo, Date published);
    public HashMap<Long, ActualItem> createCopies(long itemId, int numCopies);
    public Loan lendCopy(long memberId, long copyId);
    public ActualItem.COPYSTATUS returnCopy(long copyId);
    public Reservation reserveCopy(long copyId, long memberId);
    public Reservation reserveItem(long itemId, long memberId);
    public String getItemType(long itemId);

    //SCREEN: RESERVATIONS
    public HashMap<Long, Reservation> getAllReservations();
    public Reservation getReservation(long reservationId);
    public Loan lendReservedItem(long reservationId);
    public void cancelReservation(long reservationId);

    //SCREEN: OVERDUE & RETURNEDLOANS
    public HashMap<Long, Loan> getAllOverdue();

    //OTHERS
    public HashMap<Long, ActualItem> getAllActualItems();
}