/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import map.Stone;

/**
 *
 * @author Udara
 */
public class DisplayStone extends DisplayObject {                                 // create DisplayStone extending from DisplayObject

    Stone stone;

    public DisplayStone(Stone s)
    {
        super("Stone",s);
        stone=s;
        setImage("Stone");                                                        // set the image 'Stone'
    }

}
