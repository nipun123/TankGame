/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;


/**
 *
 * @author Udara
 */
public class Painter {
    private Engine engine;
    private PlayMap pm;
    private Hud hud;

    public Painter(Engine eng)
    {
        hud=Hud.getInstance();
        pm=new PlayMap();
        hud.intialize(eng);
        pm.initialize(eng);

    }

    public void drawHud()                                                        // drwaHud method call by GameEngine
    {
        hud.drawHud();
    }

    public void drawMap()                                                        // drawMap method call by GameEngine
    {
        pm.drawGMap();
    }


}
