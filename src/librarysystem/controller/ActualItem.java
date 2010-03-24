/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import librarysystem.LibSystem;

/**
 *
 * @author sim
 */
public class ActualItem implements java.io.Serializable {
    private long itemId;
    private long copyId;
    private boolean isBorrowed;
    private boolean isReserved;

    public enum COPYSTATUS {
        TO_SHELF, TO_RESERVE_BOX
    }

    public ActualItem(long itemId) {
        this.itemId = itemId;
        copyId = SimulatedIdGenerator.getInstance().generateId();
        isBorrowed = false;
        isReserved = false;
    }

    public void lend() {
        if (!isBorrowed() && !isReserved()) {
            isBorrowed = true;
            LibraryItem item = LibSystem.getInstance().getItem(itemId);
            item.setAvailableCopyCount(item.getAvailableCopyCount() - 1);
        }
    }
    public void checkin() {
        isBorrowed = false;
        LibraryItem item = LibSystem.getInstance().getItem(itemId);
        item.setAvailableCopyCount(item.getAvailableCopyCount() + 1);
    }
    public void reserve() {
        isReserved = true;
    }
    public void cancelReservation() {
        isReserved = false;
    }
    public void reservationIsDone() {
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
