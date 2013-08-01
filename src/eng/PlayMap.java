/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import jgame.*;
import jgame.platform.JGEngine;
import map.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Udara
 */
public class PlayMap implements Listner {

    private Engine engine;
    private Map map = Map.getInstance();;
    private GameObject[][] detMap;
    private char[][] simMap;
    List<Tank> tanks = map.getTanks();
    List<Water> water = map.getWater();
    List<Brick> bricks = map.getBricks();
    List<Coin> coins = map.getCoins();
    List<LifePack> lifepacks = map.getLifePack();
    List<Stone> stones = map.getStones();
    List<DisplayCoin> discoins = new ArrayList<DisplayCoin>();
    List<DisplayBrick> disbricks = new ArrayList<DisplayBrick>();
    List<DisplayBullet> disbullets = new ArrayList<DisplayBullet>();
    List<DisplayTank> distanks = new ArrayList<DisplayTank>();
    List<DisplayLifePack> dislifepacks = new ArrayList<DisplayLifePack>();

    public PlayMap() {
        detMap = map.getDetails();
        simMap = map.getsimMap();
        map.regisMapListner(this);
    }

    void drawGMap() {
    }

    public void initialize(Engine eng) {
        this.engine = eng;
    }

    private void drawMapI() {
        for (Brick b : bricks) {
            System.out.println("diplay brick added");
            //.add(new DisplayBrick(b));
            new DisplayBrick(b);
        }
        for (Stone s : stones) {
            new DisplayStone(s);
        }
        for (Water w : water) {
            new DisplayWater(w);
        }
    }

    private void drawMapG() {
        for (DisplayBrick db : disbricks) {
            if (db.brick.health == 0) {
                db.markRemoval();
            }
        }
        if (distanks.size() < tanks.size()) {
            boolean create = true;
            for (Tank t : tanks) {
                for (DisplayTank dt : distanks) {
                    if (dt.tank == t) {
                        create = false;
                    }
                }
                if (create) {
                    distanks.add(new DisplayTank(t));
                }

            }

        }

    }

    public void onStateChange(Map map) {
        char state = map.getMapState();
        if (state == 'G') {
            drawMapG();
        } else if (state == 'L') {//new L pack added to the end of list.We know this as this is an array list. make a game obj
            LifePack lp = lifepacks.get(lifepacks.size() - 1);//get last element
            //System.err.println(lp);
            dislifepacks.add(new DisplayLifePack(lp));
        } else if (state == 'C') {
            Coin c = coins.get(coins.size() - 1);
            System.err.print(c);
            DisplayCoin gc = new DisplayCoin(c);
            //System.err.println(gc.x+"  y="+gc.y);
            discoins.add(gc);
        } else if (state == 'S') {//now we know all the tanks.// make tank game objects to show graphic.
            List<Tank> tanks = map.getTanks();
            for (Tank t : tanks) {
                if (t.name.contains(Integer.toString(map.getOurNUm()))) {
                    distanks.add(new DisplayOurTank(t));
                } else {
                    distanks.add(new DisplayTank(t));
                }
            }
        } else if (state == 'I') {
            drawMapI();
        }

    }

    public void onLifePackVanish(LifePack lp) {
        //remove lpacks and coins wihc are vanished

        for (DisplayLifePack dl : dislifepacks) {
            if (dl.lifepack.reStep <= 0) {
                dl.markRemoval();
            }
        }
    }

    public void onCoinVanish(Coin co) {
        for (DisplayCoin dc : discoins) {
            if (dc.coin.reStep <= 0) {
                dc.markRemoval();
            }
        }
    }

    public void onLifePackPick(LifePack lp) {
        for (DisplayLifePack dl : dislifepacks) {
            if (dl.lifepack.reStep <= 0) {
                dl.markRemoval();
            }
        }
    }

    public void onCoinPick(Coin co) {
        for (DisplayCoin dc : discoins) {
            if (dc.coin.reStep <= 0) {
                dc.markRemoval();
            }
        }
    }

    public void onBulletHit(Bullet bu) {
        System.out.println("bullet hit");
        for (DisplayBullet bul : disbullets) {
            if (bul.bullet == bu) {
                bul.markRemoval();
            }
        }
    }

    public void onBulletShoot(Bullet bu) {
        disbullets.add(new DisplayBullet(bu));
    }
}
