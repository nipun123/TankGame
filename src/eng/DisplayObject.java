/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import jgame.JGObject;
import map.GameObject;

/**
 *
 * @author Udara
 */
public class DisplayObject extends JGObject {                                    // create DisplayObjects extending JGObjects

    GameObject object;
    boolean remove;

    public DisplayObject(String name, GameObject obj) {                          // Constructor for DisplayObjects
        super(name, true, 0, 0, 0, null);
        object = obj;
        setPos(object.x * Settings.tileWidth, object.y * Settings.tileHeight);   // set position of the object
    }

    @Override
    public void paint() {
    }

    public void markRemoval() {                                                  // method to mark as remove
        remove = true;
    }
}
