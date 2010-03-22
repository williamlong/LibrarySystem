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
public abstract class LibraryItem {
    private long itemId;
    private String title;
    private int maxCheckoutLength;
    private int copyCount;
    private int availableCopyCount;
    private List<ActualItem> copies;

    public LibraryItem(String title, int maxCheckoutLength) {
        this.itemId = System.currentTimeMillis();
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.copyCount = 0;
        this.availableCopyCount = 0;
        copies = new ArrayList<ActualItem>();
    }

    public List<ActualItem> getCopies() {
        return this.copies;
    }
    public void addCopy(ActualItem copy) {
        copies.add(copy);
    }
    public long getItemId() {
        return itemId;
    }

    public abstract void reserve();
}
