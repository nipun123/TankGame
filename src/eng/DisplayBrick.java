/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import map.Brick;

/**
 *
 * @author Udara
 */
public class DisplayBrick extends DisplayObject{                          // create display brick extended from DisplayObject

    Brick brick;

    public DisplayBrick(Brick b)
    {
        super("brick",b);
        brick=b;                                                          // Assingn Brick object
        setImage("Brick");                                                // set image for brick

    }

    @Override
    public void move()                                                    // Overide the move method which call by the engine to move objects
    {
        if(remove==true)                                                  // if Brick is mark to remove ,Destroy the DisplayBrick object
            this.remove();
        if(brick.health==0)                                               // if the brick heath is zero mark the brick to remove
        {
            remove=true;
            this.remove();
        }
    }


}
