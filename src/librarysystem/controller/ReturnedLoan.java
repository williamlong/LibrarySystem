/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sim
 */
public class ReturnedLoan {
    private long returnedLoanId;
    private long loanId;
    private Date actualReturnedDate;
    private boolean wasOverdue;
    private int overdueDays;

    public ReturnedLoan(long loanId, Date actualReturendDate,
            boolean wasOverdue, int overdueDays) {
        returnedLoanId = SimulatedIdGenerator.getInstance().generateId();
        this.loanId = loanId;
        this.actualReturnedDate = actualReturnedDate;
        this.wasOverdue = wasOverdue;
        this.overdueDays = overdueDays;
    }
    public long getLoanId() {
        return loanId;
    }
    public long getReturnedLoanId() {
        return returnedLoanId;
    }
}
