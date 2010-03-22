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

    private static List<Member> allMembers;

    public Member(String fname, String lname, String address, String telNo) {
        memberId = SimulatedIdGenerator.getInstance().generateId();
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.telNo = telNo;

        reservations = new ArrayList<Reservation>();
        loans = new ArrayList<Loan>();

        if(allMembers == null) {
            allMembers = new ArrayList<Member>();
        }
        allMembers.add(this);
    }

    public long getMemberId() {
        return memberId;
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
    public String getName() {
        return fname + " " + lname;
    }

    public static List<Member> getAllMembers() {
        return allMembers;
    }
}
