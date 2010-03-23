/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sim
 */
public class LibraryItem {
    private long itemId;
    private String title;
    private int maxCheckoutLength;
    private int copyCount;
    private int availableCopyCount;
    private HashMap<Long, ActualItem> copies;

    protected LibraryItem(String title, int maxCheckoutLength) {
        itemId = SimulatedIdGenerator.getInstance().generateId();
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.copyCount = 0;
        this.availableCopyCount = 0;
        copies = new HashMap<Long, ActualItem>();
    }

    public HashMap<Long, ActualItem> getCopies() {
        return copies;
    }
    public void addCopy(ActualItem copy) {
        copies.put(copy.getCopyId(), copy);
    }
    public long getItemId() {
        return itemId;
    }
    public String getTitle() {
        return title;
    }
    public void reserve() {
    }
}
