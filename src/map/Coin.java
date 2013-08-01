/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import comm.Time;

/**
 *
 * @author udara
 */
public class Coin extends GameObject {

    public int value;
    public long lifeTime;                                      // Lifetime time of the coin pack
    public int steps;
    public long creAt;                                         // Created time
    public long reTime;                                        // Remain live time of coin
    public int creStep;
    public int reStep;

    public Coin(int x, int y, long lt, int val) {
        this.x = x;
        this.y = y;
        this.lifeTime = lt;                                    // Assign the lifetime of coin
        this.value = val;
        id = 'C';
        creAt=System.currentTimeMillis();                      // Assign create at time

        System.out.println(System.currentTimeMillis() - Time.getInstance().startTime() + " :C " + getString());
    }

   
    public String getString() {
        return "Coin at " + x + ", " + y + " val=" + value;
    }
}
