/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import map.*;
import comm.Time;
import comm.Interpretor;
import jgame.platform.JGEngine;

/**
 *
 * @author Udara
 */
public class ManualPlayer implements Listner {

    private JGEngine engine;
    private Map map;
    private int ax, ay;
    private Time time;
    private int step;
    private int actKey;
    private Tank our;
    private Interpretor inte;
    private boolean started;

    public ManualPlayer(JGEngine eng) {
        this.engine = eng;
        this.map = Map.getInstance();
        this.time = Time.getInstance();
        step = time.Steps();
        this.inte = Interpretor.getInstance();
        started = false;
        map.regisMapListner(this);
    }

    public void GameControll() {
        if (started) {
            int key = engine.getLastKey();
            if (!(step < time.Steps())) {
                if (actKey == key) {
                } else {
                    if (time.getRemainTime() > 10) {
                        if (key != 0) {
                            doneKeyAction(key);
                        }
                    }
                }
            } else {
                actKey = 0;
                step = time.Steps();
                time.print();
            }
            markCommand(actKey);
            engine.clearLastKey();

        }
    }

    private void doneKeyAction(int key) {
        int x, y;
        x = our.x;
        y = our.y;
        char dir = our.dir;
        actKey = key;
        switch (key) {
            case JGEngine.KeyUp: {
                ax = x;
                ay = y - 1;
                engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowUP");
                inte.up();
                break;
            }
            case JGEngine.KeyDown: {
                ax = x;
                ay = y + 1;
                engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowDown");
                inte.down();
                break;
            }
            case JGEngine.KeyLeft: {
                ax = x - 1;
                ay = y;
                engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowLeft");
                inte.left();
                break;
            }
            case JGEngine.KeyRight: {
                ax = x + 1;
                ay = y;
                engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowRight");
                inte.right();
                break;
            }
            case JGEngine.KeyEnter: {
                ax = x;
                ay = y;
                engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowUP");
                inte.shoot();
                break;
            }

        }


    }

    private void markCommand(int key) {

        if(our==null)
        {
            our=map.getTanks().get(map.getOurNUm());
        }
        int x,y;
        char dir;
        x=our.x;
        y=our.y;
        actKey=key;

        switch (key) {
	    case JGEngine.KeyUp: {
		ax = x;
		ay = y - 1;
		engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowUP");
		break;
	    }
	    case JGEngine.KeyDown: {
		ax = x;
		ay = y + 1;
		engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowDown");
		break;
	    }
	    case JGEngine.KeyLeft: {
		ax = x - 1;
		ay = y;
		engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowLeft");
		break;
	    }
	    case JGEngine.KeyRight: {
		ax = x + 1;
		ay = y;
		engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowRight");
		break;
	    }
	    case JGEngine.KeyEnter: {
		ax = x;
		ay = y;
		engine.drawImage(ax * Settings.tileWidth, ay * Settings.tileHeight, "ArrowUP");
		break;
	    }
	}

    }


    public void onStateChange(Map map) {
        if(map.getMapState()=='S')
        {
            our=map.getTanks().get(map.getOurNUm());
            started=true;
        }
    }

    public void onLifePackVanish(LifePack lp) {
    }

    public void onCoinVanish(Coin co) {
    }

    public void onLifePackPick(LifePack lp) {
    }

    public void onCoinPick(Coin co) {
    }

    public void onBulletHit(Bullet bu) {
    }

    public void onBulletShoot(Bullet bu) {
    }
}
