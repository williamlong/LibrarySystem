/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 * @author sim
 */
public class ActualItem implements java.io.Serializable {
    private long itemId;
    private long copyId;
    private boolean isBorrowed;
    private boolean isReserved;

    public ActualItem(long itemId) {
        this.itemId = itemId;
        copyId = SimulatedIdGenerator.getInstance().generateId();
        isBorrowed = false;
        isReserved = false;
    }

    /**
     * Lending happens when there is a real copy available handed down to the librarian.
     * 
     * @param memberId
     * @param maxCheckoutLength
     */
    public Loan lend(Member member, int maxCheckoutLength) {
        isBorrowed = true;
        isReserved = false;
        
        Loan loan = new Loan(member.getMemberId(), copyId, maxCheckoutLength);
        member.addLoan(copyId, loan);
        return loan;
    }
    public ReturnedLoan checkin(Member member, Loan loan) {
        isBorrowed = false;

        member.removeLoan(copyId);
        loan.setLoanDone();

        ReturnedLoan returnedLoan = new ReturnedLoan(
                loan.getLoanId(),
                GregorianCalendar.getInstance().getTime(),
                loan.isOverdue(),
                loan.getOverdueDays());

        member.addReturnedLoan(returnedLoan);

        //FORGOT: increment item available count

        return returnedLoan;
    }
    public Reservation reserve(Member member) {
        isReserved = true;

        Reservation reservation = new Reservation(member.getMemberId(), itemId);
        member.addReservation(copyId, reservation);

        return reservation;
    }
    public void cancelReservation() {
        isReserved = false;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }
    public boolean isReserved() {
        return isReserved;
    }
    public long getCopyId() {
        return copyId;
    }
    public long getItemId() {
        return itemId;
    }
}
