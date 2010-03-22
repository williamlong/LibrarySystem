/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.List;
import java.util.ArrayList;

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

    private List<Reservation> reservations;
    private List<Loan> loans;
    private List<ReturnedLoan> returnedLoans;

    public Member(String fname, String lname, String address, String telNo) {
        memberId = System.currentTimeMillis();
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.telNo = telNo;

        reservations = new ArrayList<Reservation>();
        loans = new ArrayList<Loan>();
    }

    public void addRervation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void addLoan(Loan loan) {
        loans.add(loan);
    }
    public void addReturnedLoan(ReturnedLoan returnedLoan) {
        returnedLoans.add(returnedLoan);
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    public List<Loan> getLoans() {
        return loans;
    }
    public List<ReturnedLoan> getLoanHistory() {
        return returnedLoans;
    }
}
