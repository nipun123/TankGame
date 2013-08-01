/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import map.*;
import comm.*;

/**
 *
 * @author Udara
 */
public class AIPlayer extends Thread implements Listner {

    Map ma = Map.getInstance();
    Interpretor inte = Interpretor.getInstance();

    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            inte.right();
            if ( ma.myTank!=null && ma.getMapState()=='G') {
                ma.myTank.x++;
            }
        }
    }

    public void onStateChange(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onLifePackVanish(LifePack lp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onCoinVanish(Coin co) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onLifePackPick(LifePack lp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onCoinPick(Coin co) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onBulletHit(Bullet bu) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onBulletShoot(Bullet bu) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
