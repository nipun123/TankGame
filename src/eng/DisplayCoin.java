/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import map.Coin;

/**
 *
 * @author Udara
 */
public class DisplayCoin extends DisplayObject {                         // create DisplayCoin extending displayObject

    Coin coin;

    public DisplayCoin(Coin c) {
        super("Coin", c);
        coin = c;
        setImage("Coins");                                              // set image to Coins
    }

    @Override
    public void move() {                                               // if coins are mark to remove remove them
        if (remove == true) {
            this.remove();
        }
    }
}
