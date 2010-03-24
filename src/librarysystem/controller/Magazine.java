/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;

/**
 *
 * @author sim
 */
public class Magazine extends LibraryItem implements java.io.Serializable {
    private int volume;
    private int issueNo;
    private Date published;

    public Magazine(String title, int maxCheckoutLength,
            int volume, int issueNo, Date published) {
        super(title, maxCheckoutLength);
        this.volume = volume;
        this.issueNo = issueNo;
        this.published = published;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public int getIssueNo() {
        return issueNo;
    }

    public Date getPublished() {
        return published;
    }

    public int getVolume() {
        return volume;
    }
    
}
