/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author sgamboa
 */
public class DateRange implements java.io.Serializable {
    private GregorianCalendar start;
    private GregorianCalendar end;

    public DateRange(Date start, Date end) {
        this.start = new GregorianCalendar();
        this.start.setTime(start);
        this.end = new GregorianCalendar();
        this.end.setTime(end);
    }
    public DateRange(Date start, int numDays) {
        this.start = new GregorianCalendar();
        this.start.setTime(start);

        //NOTE: We have to clone as GregorianCalendar is mutable.
        this.end = (GregorianCalendar) this.start.clone();
        this.end.add(Calendar.DAY_OF_MONTH, numDays);
    }

    public boolean inRange(Date testDate) {
        GregorianCalendar t = new GregorianCalendar();
        t.setTime(testDate);
        if(t.compareTo(this.start) >= 0 && t.compareTo(this.end) <= 0)
            return true;
        else
            return false;
    }
    public int daysLate(Date testDate) {
        int days = 0;

        GregorianCalendar t = new GregorianCalendar();
        t.setTime(testDate);

        //after
        if(t.compareTo(this.end) > 0) {
            //NOTE: We have to clone as GregorianCalendar is mutable.
            Calendar date = (Calendar) this.end.clone();
            while(date.before(t)) {
                date.add(Calendar.DAY_OF_MONTH, 1);
                days++;
            }
        }
        //before
        if(t.compareTo(this.start) < 0) {
            //NOTE: We have to clone as GregorianCalendar is mutable.
            Calendar date = (Calendar) this.start.clone();
            while(date.after(t)) {
                date.add(Calendar.DAY_OF_MONTH, -1);
                days--;
            }
        }
        return days;
    }
    public GregorianCalendar getStart() {
        return (GregorianCalendar) this.start.clone();
    }
    public GregorianCalendar getEnd() {
        return (GregorianCalendar) this.end.clone();
    }
    public void setNewEndDate(GregorianCalendar newEndDate) {
        end = newEndDate;
    }
}
