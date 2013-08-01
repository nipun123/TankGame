/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import jgame.*;
import jgame.platform.*;
import map.*;
import eng.Painter;

/**
 *
 * @author Udara
 */
public class Engine extends JGEngine {

    private Map map;
    private boolean manual;
    private static Engine engine;
    private Painter painter;
    private ManualPlayer mplayer;

    public static Engine getEngine() {
        if (engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    public static void main(String[] args) {
        new Engine();
    }

    public Engine() {
        super();
        map = Map.getInstance();
        this.painter = new Painter(this);
        this.mplayer = new ManualPlayer(this);
        manual = true;
        Settings.initialize();
        initEngine(Settings.tileWidth * (map.getWidth() + Settings.hudWidth), Settings.tileHeight * (map.getHeight() + Settings.hudHeight));
        System.out.println("engine initiated");

    }

    public void initCanvas() {
        System.out.println("bla bla bla bla");
        setCanvasSettings(map.getWidth() + Settings.hudWidth, map.getHeight() + Settings.hudHeight, Settings.tileWidth, Settings.tileHeight, JGColor.black, JGColor.green, null);
        
    }

    public void initGame() {
        System.out.println("initgame called");
        setFrameRate(Settings.fps, 2);
        Settings.defineMedia(this);
    }

    public void paintFrame() {
        
        painter.drawHud();
        if (manual == true) {
            mplayer.GameControll();
        }
    }

    public void doFrame() {
         painter.drawMap();
         moveObjects();
    }

    public void setManulaPlay(boolean mp) {
        manual = mp;
    }
}
