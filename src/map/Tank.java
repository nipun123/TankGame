/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

/**
 *
 * @author udara
 */
public class Tank extends GameObject{

    public String name;                                    // Tank name
    public int health;                                     // Tank health
    public int points;                                     // Number of points gain by Tank
    public int coins;                                      // Amount of coins gain by Tank
    public char dir;                                       // Tank direction
    public boolean shoot,removed;                          // tank Shoot and tank removed status
    public int rank;                                       // Tank' s rank in the game
    private static int tankNum;                            // Number of tanks

    public Tank(String name,int x,int y,char dir)          // Constructor if tank details are given
    {
        this.name=name;
        this.x=x;
        this.y=y;
        this.dir=dir;
        health=100;                                        // Health assigned to 100 at the begining
        coins=0;                                           // Obtained coins assigned to 0
        points=0;                                          // Obtained coins assigned to 0
        shoot=false;                                       // Shoot set to false
        id='T';                                            // Assigned id 'T'
        tankNum++;                                         // increment tankNum

    }

    public Tank()                                          // Constructor if tank details are not given
    {
      name="not initializes";
      dir='U';
      health=100;
      coins=0;
      points=0;
      id='T';
      tankNum++;
    }


}
