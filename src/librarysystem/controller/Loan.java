/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 *
 * @author sim
 */
public class Loan {
    private long loanId;
    private long copyId;
    private long memberId;
    private boolean doneLoan;
    private DateRange borrowPeriod;

    public Loan(long memberId, long copyId, int maxCheckoutLength) {
        loanId = SimulatedIdGenerator.getInstance().generateId();
        this.memberId = memberId;
        this.copyId = copyId;
        doneLoan = false;
        borrowPeriod = new DateRange(Calendar.getInstance().getTime(), maxCheckoutLength);
    }

    public boolean isOverdue() {
        Date today = Calendar.getInstance().getTime();
        return borrowPeriod.daysLate(today) > 0;
    }
    public int getOverdueDays() {
        Date today = Calendar.getInstance().getTime();
        return borrowPeriod.daysLate(today);
    }
    public void renew(int extensionDays) {
        //Basic renew logic
        GregorianCalendar orgEndCal = borrowPeriod.getEnd();
        GregorianCalendar newEndDate = (GregorianCalendar) orgEndCal.clone();
        newEndDate.add(Calendar.DAY_OF_MONTH, extensionDays);
        borrowPeriod.setNewEndDate(newEndDate);
    }
     public void setLoanDone() {
        doneLoan = true;
    }
    public long getLoanId() {
        return loanId;
    }
    public long getCopyId() {
        return copyId;
    }

    //public static List<Loan> getAllOverdue() {
    //    List<Loan> allOverdue = new ArrayList<Loan>();
    //
    //    Collection<Loan> loans = allLoans.values();
    //    for(Loan loan : loans) {
    //        if(loan.isOverdue()) allOverdue.add(loan);
    //    }
    //    return allOverdue;
    //}
}
