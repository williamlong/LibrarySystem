/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author sim
 */
public class Loan {
    private long loanId;
    private long copyId;
    private long memberId;
    private DateRange borrowPeriod;
    private static List<Loan> allLoans;

    public Loan(long memberId, long copyId, int maxCheckoutLength) {
        this.memberId = memberId;
        this.copyId = copyId;
        borrowPeriod = new DateRange(Calendar.getInstance().getTime(), maxCheckoutLength);

        if(allLoans == null) {
            allLoans = new ArrayList<Loan>();
        }
        allLoans.add(this);
    }

    public boolean isOverdue() {
        Date today = Calendar.getInstance().getTime();
        return borrowPeriod.daysLate(today) > 0;
    }
    public void renew(int extensionDays) {
        //Basic renew logic
        GregorianCalendar orgEndCal = borrowPeriod.getEnd();
        GregorianCalendar newEndDate = (GregorianCalendar) orgEndCal.clone();
        newEndDate.add(Calendar.DAY_OF_MONTH, extensionDays);
        borrowPeriod.setNewEndDate(newEndDate);
    }
    public String getActualItemTitle() {
        long itemId = ActualItem.getActualItem(copyId).getItemId();
        LibraryItem libItem = LibraryItem.getItem(itemId);
        return libItem.getTitle();
    }

    public static List<Loan> getAllOverdue() {
        List<Loan> allOverdue = new ArrayList<Loan>();
        for(Loan loan : allLoans) {
            if(loan.isOverdue()) allOverdue.add(loan);
        }
        return allOverdue;
    }
}
