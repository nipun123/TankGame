/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import map.LifePack;

/**
 *
 * @author Udara
 */
public class DisplayLifePack extends DisplayObject{                               // create DisplayLifePack extending DisplayObjects
    LifePack lifepack;

    public DisplayLifePack(LifePack l)
    {
        super("LifePack",l);
        lifepack=l;
        setImage("Life");                                                        // set image to lifePack
    }

    @Override
    public void move()                                                           // move method to remove lifepack if it is marked to remove
    {
        if(remove==true)
            this.remove();
    }

}
