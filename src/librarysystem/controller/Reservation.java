/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author sim
 */
public class Reservation {
    private long reservationId;
    private long itemId;
    private long memberId;
    private Date reservedDate;

    public Reservation(long memberId, long itemId) {
        reservationId = System.currentTimeMillis();
        this.memberId = memberId;
        this.itemId = itemId;
        reservedDate = Calendar.getInstance().getTime(); //today
    }

    public Date getReservedDate() {
        return this.reservedDate;
    }
    public long getItemId() {
        return this.itemId;
    }
    public long getMemberId() {
        return this.memberId;
    }
    public long getReservationId() {
        return this.reservationId;
    }
}
