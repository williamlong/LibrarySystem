/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 *
 * @author sim
 */
public class Member {
    private long memberId;
    private String fname;
    private String lname;
    private String address;
    private String telNo;

    private HashMap<Long, Reservation> reservations;
    private HashMap<Long, Loan> loans;
    private HashMap<Long, ReturnedLoan> returnedLoans;

    public Member(String fname, String lname, String address, String telNo) {
        memberId = SimulatedIdGenerator.getInstance().generateId();
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.telNo = telNo;

        reservations = new HashMap<Long, Reservation>();
        loans = new HashMap<Long, Loan>();
        returnedLoans = new HashMap<Long, ReturnedLoan>();
    }

    public long getMemberId() {
        return memberId;
    }
    public void addReservation(long copyId, Reservation reservation) {
        reservations.put(copyId, reservation);
    }
    public void removeReservation(long copyId, Reservation reservation) {
        reservations.remove(copyId);
    }
    public void addLoan(long copyId, Loan loan) {
        loans.put(copyId, loan);
    }
    public void removeLoan(long copyId) {
        loans.remove(copyId);
    }
    public void addReturnedLoan(ReturnedLoan returnedLoan) {
        returnedLoans.put(returnedLoan.getReturnedLoanId(), returnedLoan);
    }
    public Collection<Reservation> getReservations() {
        return reservations.values();
    }
    public Collection<Loan> getLoans() {
        return loans.values();
    }
    public Collection<ReturnedLoan> getLoanHistory() {
        return returnedLoans.values();
    }
    public String getName() {
        return fname + " " + lname;
    }
}
