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

    //NOTE: Temporary while we don"t have persistence / db to store record
    //      and be able to retrieve objects from record ID.
    private static HashMap<Long, LibraryItem> items;

    public LibraryItem(String title, int maxCheckoutLength) {
        itemId = SimulatedIdGenerator.getInstance().generateId();
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.copyCount = 0;
        this.availableCopyCount = 0;
        copies = new HashMap<Long, ActualItem>();

        if(items == null) {
            items = new HashMap<Long, LibraryItem>();
        }
        items.put(itemId, this);
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

    public static LibraryItem getItem(long itemId) {
        return items.get(itemId);
    }
}
