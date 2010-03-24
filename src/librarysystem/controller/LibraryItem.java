
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import librarysystem.LibSystem;

/**
 *
 * @author sim
 */
public abstract class LibraryItem implements java.io.Serializable {
    private long itemId;
    private String title;
    private int maxCheckoutLength;
    private int copyCount;
    private int availableCopyCount;
    private int memberReservationCount;
    private HashMap<Long, ActualItem> copies;

    protected LibraryItem(String title, int maxCheckoutLength) {
        itemId = SimulatedIdGenerator.getInstance().generateId();
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.copyCount = 0;
        this.availableCopyCount = 0;
        memberReservationCount = 0;
        copies = new HashMap<Long, ActualItem>();
    }

    public HashMap<Long, ActualItem> getCopies() {
        return copies;
    }
    public void addCopies(int copyNum) {
        ActualItem copy = null;
        for (int i =0; i < copyNum; i++){
            copy = new ActualItem(itemId);
            this.copies.put(copy.getCopyId(), copy);
            LibSystem.getInstance().getAllActualItems().put(copy.getCopyId(), copy);
        }
        copyCount += copyNum;
        availableCopyCount += copyNum;
    }
    public long getItemId() {
        return itemId;
    }
    public String getTitle() {
        return title;
    }
    public int getMemberReservationCount() {
        return memberReservationCount;
    }
    public void setMemberReservationCount(int memberReservationCount) {
        this.memberReservationCount = memberReservationCount;
    }
    public void setAvailableCopyCount(int availableCopyCount) {
        this.availableCopyCount = availableCopyCount;
    }
    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }
    public int getAvailableCopyCount() {
        return availableCopyCount;
    }
    public int getCopyCount() {
        return copyCount;
    }
    public int getMaxCheckoutLength() {
        return maxCheckoutLength;
    }
    public void reserve() {
        memberReservationCount++;
    }
    public void unreserve() {
        memberReservationCount--;
    }
    public abstract String getType();
}
