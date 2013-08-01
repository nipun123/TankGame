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
public class LifePack extends GameObject {

    public long lifTime;                                  // LifePack 's life time
    public int steps;
    public long creAt;                                    // Life pack created time
    public long reTime;                                   // Remain Life time of life Pack
    public int creStep;
    public int reStep;

    public LifePack(int x, int y, long lt) {
        this.x = x;
        this.y = y;
        this.lifTime = lt;                                  // Assign Life time
        id = 'L';
        creAt=System.currentTimeMillis();                   // assign created time

        System.out.println(System.currentTimeMillis() - Time.getInstance().startTime() + " : " + getString());

    }

 
     public String getString() {

       return "lifPack at "+x+", "+y;
    }
}
