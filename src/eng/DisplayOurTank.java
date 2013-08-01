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
public class DisplayOurTank extends DisplayTank {                                // crate ourDisplayTank extending DisplayTank
    Tank tank;

    public DisplayOurTank(Tank t)
    {
        super(t);
        tank=t;
        setGraphic("OurTank");                                                   // set image to our tank
	System.out.println("new GOOurTank  created for t="+t.name);

    }

    @Override
    public void move()                                                           //  move method to move tank
    {
      setPos(tank.x*Settings.tileWidth, tank.y*Settings.tileHeight);             // set position of the moving tank
      if(tank.dir=='U')                                                          // if tank is moving upwards set image 'OurTankU'
          setGraphic("OurTankU");
      else if(tank.dir=='D')                                                     // if tank is moving downwards set image 'OurTankD'
          setGraphic("OurTankD");
      else if(tank.dir=='R')                                                     // if tank is moving right set image 'OurTankR'
          setGraphic("OurTankR");
      else                                                                       // if tank is moving left  set image 'OurTankL'
          setGraphic("OurTankL");
    }

    @Override
    public void paint()
    {
       super.paint();
    }

}
