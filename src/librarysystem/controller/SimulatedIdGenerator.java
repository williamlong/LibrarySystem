/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package librarysystem.controller;

/**
 *
 * @author sgamboa
 */
public class SimulatedIdGenerator {
    private static SimulatedIdGenerator instance;
    private SimulatedIdGenerator() {}
    
    public static SimulatedIdGenerator getInstance() {
        if(instance == null) {
            instance = new SimulatedIdGenerator();
        }
        return instance;
    }
    public long generateId() {
        try {
            java.lang.Thread.sleep(10, 20);
        }
        catch(java.lang.InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return System.currentTimeMillis();
        }
    }
}
