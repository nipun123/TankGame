/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

/**
 *
 * @author udara
 */
public class Bullet extends GameObject{

    /**
     * up(north)='U'
     * right(east)='R'
     * down(south)='D'
     * left(west)='L'
     */

    public char dir;                             // Bullet direction
    public Tank shotby;                          // Tank which shoot the bullet
    public int shotAt;                           // Tank target by the bullet
    public int sorcx,sorcy;                      // Bullet release location

    public Bullet()
    {
        id='X';                                  // Assign Bullet id 'X'
    }

}
