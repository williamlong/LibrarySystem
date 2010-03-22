/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

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
    public void lend(Member member, int maxCheckoutLength) {
        isBorrowed = true;
        isReserved = false;
        
        Loan loan = new Loan(member.getMemberId(), copyId, maxCheckoutLength);
        member.addLoan(loan);
    }
    public void checkin() {
        isBorrowed = false;
    }
    public void reserve() {
        isReserved = true;
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
