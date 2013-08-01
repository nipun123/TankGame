/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import map.Water;

/**
 *
 * @author Udara
 */
public class DisplayWater extends DisplayObject {                                // Create  DisplayWater by extending DisplayObject
    Water water;

    public DisplayWater(Water w)
    {
        super("Water",w);
        water=w;
        setImage("Water");                                                       // set Image to water
    }

}
