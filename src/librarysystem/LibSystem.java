/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem;

import java.util.Collections;
import java.util.GregorianCalendar;
import librarysystem.controller.ActualItem;
import librarysystem.controller.Magazine;
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
    private HashMap<Long, Reservation> allReservations;
    private HashMap<Long, ReturnedLoan> allReturnedLoans;

    private LibSystem() {
        allActualItems = new HashMap<Long, ActualItem>();
        allItems = new HashMap<Long, LibraryItem>();
        allLoans = new HashMap<Long, Loan>();
        allMembers = new HashMap<Long, Member>();
        allReservations = new HashMap<Long, Reservation>();
        allReturnedLoans = new HashMap<Long, ReturnedLoan>();
    }

    public static ILibrarySystem getInstance() {
        if(libSystem == null) {
            libSystem = new LibSystem();
        }
        return libSystem;
    }    

    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("LibSystem.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
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
    public Book createBook(String title, int maxCheckoutLength, int numCopies, String isbn, int version, Date published, List<Author> authors) {
        Book b = new Book(title, maxCheckoutLength, isbn, version, published);
        allItems.put(b.getItemId(), b);
        createCopies(b.getItemId(), numCopies);
        for(Author author : authors) {
            b.addAuthor(author);
        }
        return b;
    }
    public Author createAuthor(String fname, String lname) {
        return new Author(fname, lname);
    }
    public void addBookAuthor(Book book, Author author) {
        book.addAuthor(author);
    }  
    public Magazine createMagazine(String title, int maxCheckoutLength, int numCopies, int volume, int issueNo, Date published) {
        Magazine magazine = new Magazine(title, maxCheckoutLength, volume, issueNo, published);
        allItems.put(magazine.getItemId(), magazine);
        createCopies(magazine.getItemId(), numCopies);
        return magazine;
    }
    public HashMap<Long, ActualItem> createCopies(long itemId, int numCopies) {
        LibraryItem item = getItem(itemId);
        item.addCopies(numCopies);
        return item.getCopies();
    }
    public Loan lendCopy(long memberId, long copyId) {
        Member member = getMember(memberId);
        ActualItem copy = getActualItem(copyId);
        Loan loan = new Loan(memberId, copyId);
        allLoans.put(loan.getLoanId(), loan);
        member.addLoan(loan);
        copy.lend();
        return loan;
    }
    public ActualItem.COPYSTATUS returnCopy(long copyId) {
        Loan loan = getActiveLoan(copyId);
        loan.setLoanDone();
        
        Member member = getMember(loan.getMemberId());
        ActualItem copy = getActualItem(loan.getCopyId());

        copy.checkin(); //change copy state

        ReturnedLoan returnedLoan = new ReturnedLoan(
                loan.getLoanId(),
                GregorianCalendar.getInstance().getTime(),
                loan.isOverdue(),
                loan.getOverdueDays());

        member.removeLoan(copyId);
        member.addReturnedLoan(returnedLoan);

        allReturnedLoans.put(returnedLoan.getReturnedLoanId(), returnedLoan);

        //TODO: Mark one available copy as reserve - cannot be lend anymore.
        //TODO: notify the first member that have reservation

        //TODO: TO_SHELF, TO_RESERVE_BOX
        //return returnedLoan;
        return ActualItem.COPYSTATUS.TO_SHELF;
    }
    public Reservation reserveCopy(long copyId, long memberId) {
        ActualItem copy = getActualItem(copyId);
        LibraryItem item = getItem(copy.getItemId());
        Reservation reservation = reserveItem(item.getItemId(), memberId);
        reservation.setStatus(Reservation.STATUS.ONSHELF);
        copy.reserve();
        return reservation;
    }
    public Reservation reserveItem(long itemId, long memberId) {
        Member member = getMember(itemId);
        LibraryItem item = getItem(itemId);
        Reservation reservation = new Reservation(itemId, memberId);
        allReservations.put(reservation.getReservationId(), reservation);
        member.addReservation(reservation);
        item.reserve();
        return reservation;
    }
    public HashMap<Long, ActualItem> getItemActualCopies(long itemId) {
        return allItems.get(itemId).getCopies();
    }
    public String getItemType(long itemId) {
        return allItems.get(itemId).getType();
    }

    //SCREEN: RESERVATIONS
    public Loan lendReservedItem(long reservationId) {
        Reservation reservation = getReservation(reservationId);
        if (reservation.getStatus() != Reservation.STATUS.ONSHELF){
            //Do some thing
            return null;
        }
        LibraryItem item = getItem(reservation.getItemId());
        Member member = getMember(reservation.getMemberId());
        member.removeReservation(reservationId);

        ActualItem copy = getAvailableReservedCopy(item);
        copy.cancelReservation();

        allReservations.remove(reservationId);

        Loan loan = lendCopy(member.getMemberId(), copy.getCopyId());
        return loan;
    }
    public void cancelReservation(long reservationId) {
        Reservation reservation = getReservation(reservationId);
        LibraryItem item = getItem(reservation.getItemId());
        item.unreserve();
        Member member = getMember(reservation.getMemberId());
        member.removeReservation(reservationId);
        allReservations.remove(reservationId);

        if (reservation.getStatus() == Reservation.STATUS.ONSHELF){
            item.setAvailableCopyCount(item.getAvailableCopyCount() + 1);
            ActualItem copy = getAvailableReservedCopy(item);
            copy.cancelReservation();
        }
    }
    //Need to adjust
    public boolean checkReservationAsFirst(long reservationId){
        boolean ret = false;
        Reservation reservation = getReservation(reservationId);

        Collection<Reservation> reservations = allReservations.values();
        List<Reservation> reservationsOfItem = new ArrayList<Reservation>();
        for (Reservation tmp : reservations){
            if (tmp.getItemId() == reservation.getItemId()){
                reservationsOfItem.add(tmp);
            }
        }
        Collections.sort(reservationsOfItem);
        ret = reservationsOfItem.get(0).getReservationId() == reservationId;
        return ret;
    }

    //SCREEN: OVERDUE
    public HashMap<Long, Loan> getAllOverdue(){
        Collection<Loan> tmpLoans = allLoans.values();
        HashMap<Long, Loan> ret = new HashMap<Long, Loan>();
        for (Loan tmpLoan : tmpLoans) {
            if (!tmpLoan.isDone() && tmpLoan.isOverdue()){
                ret.put(tmpLoan.getLoanId(), tmpLoan);
            }
        }
        return ret;
    }

    //Don't need to show on screen
    public HashMap<Long, Loan> getAllUnreturnedLoans(){
        Collection<Loan> tmpLoans = allLoans.values();
        HashMap<Long, Loan> ret = new HashMap<Long, Loan>();
        for (Loan tmpLoan : tmpLoans) {
            if (!tmpLoan.isDone()){
                ret.put(tmpLoan.getLoanId(), tmpLoan);
            }
        }
        return ret;
    }

    //GET ALL
    public HashMap<Long, ActualItem> getAllActualItems() { return allActualItems; }
    public HashMap<Long, LibraryItem> getAllItems() { return allItems; }
    public HashMap<Long, Loan> getAllLoans() { return allLoans; }
    public HashMap<Long, Member> getAllMembers() { return allMembers; }
    public HashMap<Long, Reservation> getAllReservations() { return allReservations; }
    public HashMap<Long, ReturnedLoan> getAllReturnedLoans() { return allReturnedLoans; }

    //GET SINGLE
    public LibraryItem getItem(long itemId) { return allItems.get(itemId); }
    public Member getMember(long memberId) { return allMembers.get(memberId); }
    public Loan getLoan(long loanId) { return allLoans.get(loanId); }
    public ReturnedLoan getReturnedLoan(long returnedLoanId) { return allReturnedLoans.get(returnedLoanId); }
    public Reservation getReservation(long reservationId) { return allReservations.get(reservationId); }
    public ActualItem getActualItem(long copyId) { return allActualItems.get(copyId); }



    //PRIVATE HELPERS
    private Loan getActiveLoan(long copyId){
        Loan ret = null;
        ActualItem copy = getActualItem(copyId);
        if (copy.isBorrowed()){
            Collection<Loan> loans = allLoans.values();
            for (Loan tmpLoan : loans){
                if (tmpLoan.getCopyId() == copyId && !tmpLoan.isDone()){
                    ret = tmpLoan;
                }
            }
        }
        return ret;
    }
    private ActualItem getAvailableReservedCopy(LibraryItem item){
        ActualItem copy = null;
        Collection<ActualItem> copies = item.getCopies().values();
        for (ActualItem tmpCopy : copies){
            if (tmpCopy.isReserved()){
                copy = tmpCopy;
                break;
            }
        }
        return copy;
    }
}
