/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sli
 */
public class Librarian {

    private HashMap<Long, Member> allMembers = new HashMap<Long, Member>();
    private HashMap<Long, LibraryItem> allItems = new HashMap<Long, LibraryItem>();
    private HashMap<Long, ActualItem> allCopies = new HashMap<Long, ActualItem>();
    private HashMap<Long, Loan> allLoans = new HashMap<Long, Loan>();
    private HashMap<Long, Reservation> allReservations = new HashMap<Long, Reservation>();
    private HashMap<Long, ReturnedLoan> allReturnedLoans = new HashMap<Long, ReturnedLoan>();
    private static Librarian instance = new Librarian();

    public Member searchMember(long memberId) {
        return allMembers.get(memberId);
    }

    public LibraryItem searchItem(long itemId) {
        return allItems.get(itemId);
    }

    public ActualItem searchCopy(long copyId) {
        return allCopies.get(copyId);
    }

    public Loan searchLoan(long loanId) {
        return allLoans.get(loanId);
    }

    public Reservation searchReservation(long reservationId) {
        return allReservations.get(reservationId);
    }

    public ReturnedLoan searchReturnedLoan(long returnedLoanId) {
        return allReturnedLoans.get(returnedLoanId);
    }

    public Member addMember(String fname, String lname, String address, String telNo) {
        Member member = new Member(fname, lname, address, telNo);
        allMembers.put(member.getMemberId(), member);
        return member;
    }

    public Book addBook(String title, int maxCheckoutLength, int copyCount, String isbnA, String isbnB, int version, Date published, List<Author> authors) {
        Book book = new Book(title, maxCheckoutLength, copyCount, isbnA, isbnB, version, published, authors);
        allItems.put(book.getItemId(), book);
        return book;
    }

    public Magazine addMagazine(String title, int maxCheckoutLength, int copyCount,
            int volume, int issueNo, Date published) {
        Magazine magazine = new Magazine(title, maxCheckoutLength, copyCount, volume, issueNo, published);
        allItems.put(magazine.getItemId(), magazine);
        return magazine;
    }

    public Loan getReservedBook(Reservation reservation) {
        if (!reservation.getStatus().equals(Reservation.STATUS.ONSHELF)) {
            throw new RuntimeException();
        }

        ActualItem copy = null;
        LibraryItem item = reservation.getItem();

        List<ActualItem> copyies = item.getCopies();
        for (ActualItem tmpCopy : copyies) {
            if (!tmpCopy.isBorrowed() && tmpCopy.isReserved()) {
                copy = tmpCopy;
                break;
            }
        }
        long copyId = copy.getCopyId();
        Member member = reservation.getMember();

        Loan loan = new Loan(member.getMemberId(), copyId);

        reservation.setStatus(Reservation.STATUS.SUCCEED);

        member.getReservations().remove(reservation);
        member.getLoans().add(loan);
        copy.lend();
        return loan;
    }

    public Loan checkOut(long memberId, long copyId) {
        Member member = this.searchMember(memberId);
        ActualItem copy = this.searchCopy(copyId);

        // NOTE : this method is not for people who reserves an item
        if (copy.isReserved()) {
            throw new RuntimeException();
        }

        Loan loan = new Loan(memberId, copyId);
        member.getLoans().add(loan);
        copy.lend();
        return loan;
    }

    public ReturnedLoan checkIn(long loanId) {
        Loan loan = this.searchLoan(loanId);
        ReturnedLoan returnedLoan = new ReturnedLoan(loanId, Calendar.getInstance().getTime());

        Member returnMember = loan.getMember();

        returnMember.getLoans().remove(loan);
        returnMember.addReturnedLoan(returnedLoan);

        ActualItem copy = loan.getCopy();

        copy.checkIn();

        //if the item is reserved, deal with it
        LibraryItem item = copy.getItem();

        if (item.getReservations().size() > 0) {
            Reservation reservation = item.getReservations().get(0);
            reservation.setStatus(Reservation.STATUS.ONSHELF);

            //TODO Code : send a notice to member
            
            Member member = reservation.getMember();
            String email = member.getAddress();
            //System.out.println("Member : " + member.getFname() + " " + member.getLname() + " Your reservation is ready!");

            //put this copy on reserved shelf
            copy.reserve();
        }
        return returnedLoan;
    }

    public Reservation reserve(long itemId, long memberId) {
        LibraryItem item = this.searchItem(itemId);
        
        if (item.getAvailableCopyCount() != 0) {
            throw new RuntimeException();
        }
        Member member = this.searchMember(memberId);
        Reservation reservation = new Reservation(itemId, memberId);
        item.getReservations().add(reservation);
        member.getReservations().add(reservation);
        return reservation;
    }

    public void cancelReservation(Reservation reservation) {
        reservation.setStatus(Reservation.STATUS.OVERDUE);
        reservation.getItem().getReservations().remove(reservation);
        reservation.getMember().getReservations().remove(reservation);
    }

    

    public static Librarian getInstance() {
        return instance;
    }

    private Librarian() {
    }
}
