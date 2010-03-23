/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem;

import java.util.GregorianCalendar;
import librarysystem.controller.ActualItem;
import librarysystem.controller.Author;
import librarysystem.controller.Member;
import librarysystem.controller.Book;
import librarysystem.controller.Magazine;
import librarysystem.controller.Loan;
import librarysystem.controller.ReturnedLoan;
import librarysystem.controller.Reservation;
import librarysystem.controller.LibraryItem;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sgamboa
 */
public class Main {
    public static void main(String[] args) {

         /*Test Plan:
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
         * 1. serach item
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

        LibSystem lib = LibSystem.getInstance();

        HashMap<Long, ActualItem> actualItems = lib.getActualItems();
        HashMap<Long, LibraryItem> items = lib.getAllItems();
        HashMap<Long, Loan> allLoans = lib.getAllLoans();
        HashMap<Long, Member> allMembers = lib.getAllMembers();
        List<Reservation> allReservations = lib.getAllReservations();
        HashMap<Long, ReturnedLoan> allReturnedLoans = lib.getAllReturnedLoans();

        //I.4 - add member
        Member m1 = new Member("Aimee", "Ardaiz", "MR# 576 1000 N 4th Fairfield, IA 52557", "(641)333-4444");
        Member m2 = new Member("Shilong", "Lee", "MR# 577 1000 N 4th Fairfield, IA 52557", "(641)333-4445");
        Member m3 = new Member("Clay", "Uy", "MR# 575 1000 N 4th Fairfield, IA 52557", "(641)333-4446");
        Member m4 = new Member("Sim", "Gamboa", "MR# 575 1000 N 4th Fairfield, IA 52557", "(641)333-4447");

        allMembers.put(m1.getMemberId(), m1);
        allMembers.put(m2.getMemberId(), m2);
        allMembers.put(m3.getMemberId(), m3);
        allMembers.put(m4.getMemberId(), m4);

        //II.4 - add item
        GregorianCalendar c1 = new GregorianCalendar(2008, 10-1, 17);
        GregorianCalendar c2 = new GregorianCalendar(2007, 8-1, 16);
        GregorianCalendar c3 = new GregorianCalendar(2010, 3-1, 10);
        GregorianCalendar c4 = new GregorianCalendar(2010, 3-1, 1);

        Book b1 = new Book("Collective Intelligence in Action", 21,
                "1933988312", "978-1933988313", 1, c1.getTime());
        Book b2 = new Book("Programming Collective Intelligence", 21,
                "", "", 1, c2.getTime());

        Magazine mag1 = new Magazine("Inc.com", 7, 2010, 310, c3.getTime());
        Magazine mag2 = new Magazine("Entrepreneur", 1, 2010, 301, c4.getTime());

        items.put(b1.getItemId(), b1);
        items.put(b2.getItemId(), b2);
        items.put(mag1.getItemId(), mag1);
        items.put(mag2.getItemId(), mag2);

        //II.14 - add author
        Author a1 = new Author("Satnam", "Alag", b1);
        Author a2 = new Author("Toby", "Segaran", b2);
        b1.addAuthor(a1); //NOTE: Improve this workflow.
        b2.addAuthor(a2);

        //II.5 - add copy
        long b1ItemId = b1.getItemId();
        long b2ItemId = b2.getItemId();
        long m1ItemId = mag1.getItemId();
        long m2ItemId = mag2.getItemId();

        ActualItem b1Copy1 = new ActualItem(b1ItemId);
        ActualItem b1Copy2 = new ActualItem(b1ItemId);
        ActualItem b1Copy3 = new ActualItem(b1ItemId);

        ActualItem b2Copy1 = new ActualItem(b2ItemId);
        ActualItem b2Copy2 = new ActualItem(b2ItemId);
        ActualItem b2Copy3 = new ActualItem(b2ItemId);

        ActualItem m1Copy1 = new ActualItem(m1ItemId);
        ActualItem m2Copy1 = new ActualItem(m2ItemId);

        actualItems.put(b1Copy1.getCopyId(), b1Copy1);
        actualItems.put(b1Copy2.getCopyId(), b1Copy2);
        actualItems.put(b1Copy3.getCopyId(), b1Copy3);
        actualItems.put(b2Copy1.getCopyId(), b2Copy1);
        actualItems.put(b2Copy2.getCopyId(), b2Copy2);
        actualItems.put(m1Copy1.getCopyId(), m1Copy1);
        actualItems.put(m2Copy1.getCopyId(), m2Copy1);

        b1.addCopy(b1Copy1);
        b1.addCopy(b1Copy2);
        b1.addCopy(b1Copy3);
        b2.addCopy(b2Copy1);
        b2.addCopy(b2Copy2);
        b2.addCopy(b2Copy3);
        mag1.addCopy(m1Copy1);
        mag2.addCopy(m2Copy1);

        //II.9 - checkout copy
        //Member: m4
        Loan l1 = b1Copy1.lend(m4, 21);
        Loan l2 = b2Copy1.lend(m4, 7);
        Loan l3 = m1Copy1.lend(m4, 7);
        Loan l4 = m2Copy1.lend(m4, 3);

        allLoans.put(l1.getLoanId(), l1);
        allLoans.put(l2.getLoanId(), l2);
        allLoans.put(l3.getLoanId(), l3);
        allLoans.put(l4.getLoanId(), l4);

        //I.5 - show member loans
        Collection<Loan> m4Loans = m4.getLoans();
        System.out.println("Member: " + m4.getName());
        System.out.println("    Loans: ");
        for(Loan loan : m4Loans) {
            long copyId = loan.getCopyId();
            long itemId = actualItems.get(copyId).getItemId();
            System.out.println("        " + items.get(itemId).getTitle());
        }

        //II.10 - return copy
        System.out.println("");
        System.out.println("Now, return a copy: 'Entrepreneur'");
        System.out.println("");

        ReturnedLoan returnedLoan = m2Copy1.checkin(m4, l4);
        allReturnedLoans.put(returnedLoan.getReturnedLoanId(), returnedLoan);

        System.out.println("Member: " + m4.getName());
        System.out.println("    Loans: ");
        for(Loan loan : m4Loans) {
            long copyId = loan.getCopyId();
            long itemId = actualItems.get(copyId).getItemId();
            System.out.println("        " + items.get(itemId).getTitle());
        }

        //II.11 - reserve book/mag
        System.out.println("");
        System.out.println("Now, an already checked-out single copy of magazine: 'Inc.com'");
        System.out.println("Make a reservation: 'Inc.com'");

        Reservation reservation = m1Copy1.reserve(m4);
        allReservations.add(reservation);

        //I.6 - show member reservations
        System.out.println("");
        System.out.println("Show user reservations: ");

        Collection<Reservation> m4Reservations = m4.getReservations();
        System.out.println("Member: " + m4.getName());
        System.out.println("    Reservations: ");
        for(Reservation r : m4Reservations) {
            System.out.println("        " + items.get(r.getItemId()).getTitle());
        }

        //I.7 - show member history
        System.out.println("");
        System.out.println("Show user loan history: ");

        Collection<ReturnedLoan> m4ReturnedLoans = m4.getLoanHistory();
        System.out.println("Member: " + m4.getName());
        System.out.println("    Returned Loans: ");
        for(ReturnedLoan rl : m4ReturnedLoans) {
            long loanId = rl.getLoanId();
            Loan loan = allLoans.get(loanId);
            long copyId = loan.getCopyId();
            ActualItem copy = actualItems.get(copyId);

            System.out.println("        " + items.get(copy.getItemId()).getTitle());
        }
    }
}
