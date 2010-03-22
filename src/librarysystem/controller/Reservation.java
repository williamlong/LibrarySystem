/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sim
 */
public class Reservation {
    private long reservationId;
    private long itemId;
    private long memberId;
    private Date reservedDate;
    private static List<Reservation> allReservations;

    public Reservation(long memberId, long itemId) {
        reservationId = SimulatedIdGenerator.getInstance().generateId();
        this.memberId = memberId;
        this.itemId = itemId;
        reservedDate = Calendar.getInstance().getTime(); //today

        if(allReservations == null) {
            allReservations = new ArrayList<Reservation>();
        }
        allReservations.add(this);
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
    public static List<Reservation> getAllReservations() {
        return allReservations;
    }
}
