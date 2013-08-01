/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import jgame.JGColor;
import jgame.JGObject;
import map.Map;
import map.Tank;

/**
 *
 * @author Udara
 */
public class DisplayTank extends DisplayObject {                                 // Create  DisplayTank extending displayObjects

    Tank tank;

    public DisplayTank(Tank t)
    {
        super(t.name,t);
        tank=t;
        xspeed=0;
        yspeed=0;
        int num=Integer.parseInt(t.name.substring(1));

        switch (num) {                                                            // set the images to tanks according to the tankNum
            case 0: {
                setGraphic("TankYellow");
                break;
            }
            case 1: {
                setGraphic("TankRed");
                break;
            }
            case 2: {
                setGraphic("TankPink");
                break;
            }
            case 3: {
                setGraphic("TankCryon");
                break;
            }
            case 4: {
                setGraphic("TankOrange");
                break;
            }
            
        }

        System.out.println("new GOTank  created for t=" + t.name);
        System.out.println(Map.getInstance().getTanks().toString());

    }

    @Override
    public void move()                                                           // Call move objects move tanks
    {
        x=(double)(tank.x*Settings.tileWidth);
        y=(double)(tank.y*Settings.tileHeight);
        if(tank.dir=='U')                                                        // set images to tank according to the tank direction
        {
            setGraphic("Tank"+tank.name.substring(1)+"U");
        }
        else if(tank.dir == 'D')
        {
            setGraphic("Tank"+tank.name.substring(1)+"D");
        }
        else if(tank.dir == 'R')
        {
            setGraphic("Tank"+tank.name.substring(1)+"R");
        }
        else if(tank.dir == 'L')
        {
            setGraphic("Tank"+tank.name.substring(1)+"L");
        }
        if(tank.health<=0)
        {
            remove=true;
            this.remove();
        }
    }

    @Override
    public void paint()
    {
       super.paint();
    }

}
