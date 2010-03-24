/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.HashMap;
import java.util.Collection;

/**
 *
 * @author sim
 */
public class Member implements java.io.Serializable {
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
    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }
    public void removeReservation(long reservationId) {
        reservations.remove(reservationId);
    }
    public void addLoan(Loan loan) {
        loans.put(loan.getLoanId(), loan);
    }
    public void removeLoan(long loanId) {
        loans.remove(loanId);
    }
    public void addReturnedLoan(ReturnedLoan returnedLoan) {
        returnedLoans.put(returnedLoan.getReturnedLoanId(), returnedLoan);
    }
    public Collection<Reservation> getReservations() {
        return reservations.values();
    }
    public HashMap<Long, Reservation> getReservationsWithKey() {
        return reservations;
    }
    public Collection<Loan> getLoans() {
        return loans.values();
    }
    public HashMap<Long, Loan> getLoansWithKey() {
        return loans;
    }
    public Collection<ReturnedLoan> getLoanHistory() {
        return returnedLoans.values();
    }
    public HashMap<Long, ReturnedLoan> getLoanHistoryWithKey() {
        return returnedLoans;
    }
    public String getName() {
        return fname + " " + lname;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getAddress() {
        return address;
    }
    public String getTelNo() {
        return telNo;
    }
}
