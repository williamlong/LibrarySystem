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
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sim
 */
public class LibSystem {
    private static LibSystem libSystem;

    private HashMap<Long, ActualItem> actualItems;
    private HashMap<Long, LibraryItem> allItems;
    private HashMap<Long, Loan> allLoans;
    private HashMap<Long, Member> allMembers;
    private List<Reservation> allReservations;
    private HashMap<Long, ReturnedLoan> allReturnedLoans;

    private LibSystem() {
        actualItems = new HashMap<Long, ActualItem>();
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
    public HashMap<Long, ActualItem> getActualItems() {
        return actualItems;
    }
    public HashMap<Long, LibraryItem> getAllItems() {
        return allItems;
    }
    public HashMap<Long, Loan> getAllLoans() {
        return allLoans;
    }
    public HashMap<Long, Member> getAllMembers() {
        return allMembers;
    }
    public List<Reservation> getAllReservations() {
        return allReservations;
    }
    public HashMap<Long, ReturnedLoan> getAllReturnedLoans() {
        return allReturnedLoans;
    }

    
}
