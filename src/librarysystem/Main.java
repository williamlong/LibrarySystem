/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import java.util.ArrayList;
import java.util.Date;
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

    public static void createData() {
        LibSystem lib = LibSystem.getInstance();

        //I.4 - add member
        Member m1 = lib.createMember("Aimee", "Ardaiz", "MR# 576 1000 N 4th Fairfield, IA 52557", "(641)333-4444");
        Member m2 = lib.createMember("Shilong", "Lee", "MR# 577 1000 N 4th Fairfield, IA 52557", "(641)333-4445");
        Member m3 = lib.createMember("Clay", "Uy", "MR# 575 1000 N 4th Fairfield, IA 52557", "(641)333-4446");
        Member m4 = lib.createMember("Sim", "Gamboa", "MR# 575 1000 N 4th Fairfield, IA 52557", "(641)333-4447");


        //II.4 - add item
        GregorianCalendar c1 = new GregorianCalendar(2008, 10 - 1, 17);
        GregorianCalendar c2 = new GregorianCalendar(2007, 8 - 1, 16);
        GregorianCalendar c3 = new GregorianCalendar(2010, 3 - 1, 10);
        GregorianCalendar c4 = new GregorianCalendar(2010, 3 - 1, 1);

        Author a1 = lib.createAuthor("Mahesh", "Yogi");
        Author a21 = lib.createAuthor("Jack", "Chen");
        Author a22 = lib.createAuthor("Harry", "Potter");

        ArrayList<Author> al1 = new ArrayList<Author>();
        al1.add(a1);
        ArrayList<Author> al2 = new ArrayList<Author>();
        al1.add(a21);
        al1.add(a22);

        Book b1 = lib.createBook("SCI Book", 21, 10, "isbn001", 3, c1.getTime(), al1);
        Book b2 = lib.createBook("Core Java 2", 7, 5, "isbn002", 8, c2.getTime(), al2);

        Magazine mag1 = lib.createMagazine("Inc.com", 7, 5, 2010, 310, c3.getTime());
        Magazine mag2 = lib.createMagazine("Entrepreneur", 21, 1, 2010, 301, c4.getTime());

        //II.14 - add author
        Author aa1 = lib.createAuthor("Satnam", "Alag");
        Author aa2 = lib.createAuthor("Toby", "Segaran");

        b2.addAuthor(aa1); //NOTE: Improve this workflow.
        b2.addAuthor(aa2);

        //II.5 - add copy
        long b2ItemId = b2.getItemId();
        long m1ItemId = mag1.getItemId();

        lib.getItem(b2ItemId).addCopies(5);
        lib.getItem(m1ItemId).addCopies(2);

        //II.9 - checkout copy
        //Member: m4

        ActualItem copy1 = b1.getCopies().values().iterator().next();
        ActualItem copy2 = mag2.getCopies().values().iterator().next();

        lib.lendCopy(m4.getMemberId(), copy1.getCopyId());
        lib.lendCopy(m4.getMemberId(), copy2.getCopyId());

        //I.5 - show member loans
        Collection<Loan> m4Loans = lib.getMember(m4.getMemberId()).getLoans();

        System.out.println("Member: " + m4.getName());
        System.out.println("    Loans: ");
        for (Loan loan : m4Loans) {
            long copyId = loan.getCopyId();
            long itemId = lib.getActualItem(copyId).getItemId();
            System.out.println("        " + lib.getItem(itemId).getTitle());
        }

        //II.10 - return copy
        System.out.println("");
        System.out.println("Now, return a copy: 'SCI Book'");
        System.out.println("");

        lib.returnCopy(copy1.getCopyId());

        System.out.println("Member: " + m4.getName());
        System.out.println("    Loans: ");
        m4Loans = m4.getLoans();
        for (Loan loan : m4Loans) {
            long copyId = loan.getCopyId();
            long itemId = lib.getActualItem(copyId).getItemId();
            System.out.println("        " + lib.getItem(itemId).getTitle());
        }

        //borrow the copy again
        lib.lendCopy(m4.getMemberId(), copy1.getCopyId());

        //II.11 - reserve book/mag
        System.out.println("");
        System.out.println("Mag1 available copies" + mag1.getAvailableCopyCount());
        System.out.println("Mag2 available copies" + mag2.getAvailableCopyCount());

        ActualItem copy3 = mag1.getCopies().values().iterator().next();

        Reservation reservation1 = lib.reserveItem(mag2.getItemId(), m3.getMemberId());
        Reservation reservation2 = lib.reserveCopy(copy3.getCopyId(), m3.getMemberId());

        lib.lendReservedItem(reservation2.getReservationId());
        lib.returnCopy(copy2.getCopyId());
        lib.lendReservedItem(reservation1.getReservationId());

        //I.6 - show member reservations
        System.out.println("");
        System.out.println("Show user reservations: ");

        Collection<Reservation> m3Reservations = m3.getReservations();

        System.out.println("Member: " + m3.getName());
        System.out.println("    Reservations: ");
        for (Reservation r : m3Reservations) {
            System.out.println("        " + lib.getItem(r.getItemId()).getTitle());
        }

        //I.7 - show member history
        System.out.println("");
        System.out.println("Show user loan history: ");

        Collection<ReturnedLoan> m4ReturnedLoans = lib.getMember(m4.getMemberId()).getLoanHistory();
        System.out.println("Member: " + m4.getName());
        System.out.println("    Returned Loans: ");
        for (ReturnedLoan rl : m4ReturnedLoans) {
            long loanId = rl.getLoanId();
            Loan loan = lib.getLoan(loanId);
            long copyId = loan.getCopyId();
            ActualItem copy = lib.getActualItem(copyId);
            System.out.println("        " + lib.getItem(copy.getItemId()).getTitle());
        }
    }

    public static void serializeData() {
        LibSystem.getInstance().serialize();
    }

    public static void deserializeData() {
        LibSystem.getInstance().deserialize();
    }

    public static void displayData() {
        LibSystem lib = LibSystem.getInstance();
        Collection<Member> members = lib.getAllMembers().values();
        for (Member member : members) {
            System.out.println("Member: " + member.getName());
            System.out.println("    Loans: ");
            Collection<Loan> loans = lib.getMemberLoans(member.getMemberId()).values();
            for (Loan loan : loans) {
                System.out.println("        Item: " + lib.getItem(lib.getActualItem(loan.getCopyId()).getItemId()).getTitle());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        //createData();
        //serializeData();
        deserializeData();
        displayData();
    }
}
