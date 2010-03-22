/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sim
 */
public class ReturnedLoan {
    private long returnedLoanId;
    private long loanId;
    private GregorianCalendar actualReturnedDate;
    private boolean wasOverdue;
    private int overdueDays;
    private static List<ReturnedLoan> allReturnedLoans;

    public ReturnedLoan(long loanId, GregorianCalendar actualReturendDate,
            boolean wasOverdue, int overdueDays) {
        returnedLoanId = System.currentTimeMillis();
        this.loanId = loanId;
        this.actualReturnedDate = actualReturnedDate;
        this.wasOverdue = wasOverdue;
        this.overdueDays = overdueDays;

        if(allReturnedLoans == null) {
            allReturnedLoans = new ArrayList<ReturnedLoan>();
        }
        allReturnedLoans.add(this);
    }
}
