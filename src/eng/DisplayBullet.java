/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import map.*;



/**
 *
 * @author Udara
 */
public class DisplayBullet extends DisplayObject{                                      //   crate Display Bullet by extending DisplayObject

    Bullet bullet;
    char dir;
    private Map map=Map.getInstance();

    public DisplayBullet(Bullet b)
    {
        super("Bullet",b);
        bullet=b;
        dir=b.dir;
        xspeed=5; // 3 tiles per second
        yspeed=5;
        x=b.x*Settings.tileWidth;                                                      // set Bullet initial x position
        y=b.y*Settings.tileHeight;                                                     // set Bullet initial y position

        if (dir == 'U') {                                                              // when bullet move up set bullet settings
	    ydir = -1;                                                                 // set y direction of GameObject
	    xspeed = 0;                                                                // set x speed to 0
            setImage("BulletU");                                                       // set BulletU image
	} else if (dir == 'D') {                                                       // when bullet moves down set bullet settings
	    ydir = 1;
	    xspeed = 0;
            setImage("BulletD");
	} else if (dir == 'L') {                                                       // when bullet moves left set bullet settings
	    yspeed = 0;
	    xdir = -1;
            setImage("BulletL");
	} else if (dir == 'R') {                                                       // when bullet moves right set bullet settings
	    yspeed = 0;
	    xdir = 1;
            setImage("BulletR");
	} else {
	    throw new RuntimeException("Wrong dir value in a tank=>wrong dir value for the shot bullet");
	}
    }

    @Override
    public void move()                                                                  //  move method to move objects
    {
        if(x>=map.getWidth()*Settings.tileWidth||y>map.getHeight()*Settings.tileHeight){// if bullet goes out of the map mark bullet to remove
            remove=true;
        }
        if(remove){                                                                     // if map is assigned to remove ,remove it
	    System.out.println("Bullet image marked for removal");
	    remove();
	}
    }

}


