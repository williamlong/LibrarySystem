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
public class ActualItem {
    private long itemId;
    private long copyId;
    private boolean isBorrowed;
    private boolean isReserved;

    //NOTE: Temporary while we don"t have persistence / db to store record
    //      and be able to retrieve objects from record ID.
    private static HashMap<Long, ActualItem> actualItems;

    public ActualItem(long itemId) {
        this.itemId = itemId;
        copyId = SimulatedIdGenerator.getInstance().generateId();
        isBorrowed = false;
        isReserved = false;

        if(actualItems == null) {
            actualItems = new HashMap<Long, ActualItem>();
        }
        actualItems.put(copyId, this);
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

    public static ActualItem getActualItem(long copyId) {
        return actualItems.get(copyId);
    }
}
