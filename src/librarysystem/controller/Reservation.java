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
public class Reservation implements java.io.Serializable, Comparable<Reservation> {
    private long reservationId;
    private long itemId;
    private long memberId;
    private Date reservedDate;
    private STATUS status;

    public enum STATUS{
        NOAVAILABLECOPY, ONSHELF
    }
    
    public Reservation(long itemId, long memberId) {
        reservationId = SimulatedIdGenerator.getInstance().generateId();
        this.memberId = memberId;
        this.itemId = itemId;
        reservedDate = Calendar.getInstance().getTime(); //today
        status = STATUS.NOAVAILABLECOPY;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
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

    public int compareTo(Reservation o) {
        return reservedDate.compareTo(o.getReservedDate());
    }
}
