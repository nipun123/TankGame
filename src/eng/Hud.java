/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eng;

import map.*;
import jgame.*;
import jgame.platform.*;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Formatter;
import java.util.ArrayList;

/**
 *
 * @author Udara
 */
public class Hud {

    private JGEngine engine;
    private Map map;
    private static Hud hud;
    private GameObject[][] detMap;
    private char[][] simMap;
    private JGFont tileNum = new JGFont(null, JGFont.ITALIC, 10);
    private JGFont font = new JGFont(null, JGFont.PLAIN, 16);
    private JGFont topic = new JGFont(null, JGFont.BOLD, 18);
    private boolean gridon;
    private boolean showTileNum;
    private List<Tank> tanks;
    private String message="Ready to start the game";
    private String leftstr="", botstr="";

    public Hud() {
        map = Map.getInstance();
        detMap = map.getDetails();
        simMap = map.getsimMap();
        tanks = map.getTanks();

        for (int i = 0; i <Settings.hudWidth ; i++) {
            leftstr += "H ";
        }
        for (int i = 0; i < Settings.hudWidth + map.getWidth(); i++) {
            botstr += "H ";
        }
    }

    public void intialize(JGEngine eng) {
        this.engine = eng;
    }

    public static Hud getInstance()
    {
        if(hud==null)
            hud=new Hud();

        return hud;
    }

    public void drawHud() {

        //eng.setTile
        engine.drawString("Game Board", Settings.groundWidth +200, 60, -1, topic, JGColor.yellow);
        if(map.myTank!=null){
            engine.drawString(map.myTank.name,Settings.groundWidth + 455, 10, -1, topic, JGColor.yellow);
        }

        //Hud tiles.//dont set
        String[] hudLft = new String[map.getHeight()];
        for (int i = 0; i < map.getHeight(); i++) {
            hudLft[i] = leftstr;
        }
        String[] hudBtm = new String[Settings.hudHeight];
        for (int i = 0; i < Settings.hudHeight; i++) {
            hudBtm[i] = botstr;
        }

        engine.setTilesMulti(map.getWidth(), 0, hudLft);
        engine.setTilesMulti(0, map.getHeight(), hudBtm);

        if (gridon) {
            drawGrid();
        }
        drawplayField();//play field boarder
        if (showTileNum) {
            drawTileNums();//play field tile numbers
        }
        drawMapState();
        drawScoreBoard();
        drawMessage();
    }

    public void drawGrid()
    {
       double width = Settings.tileWidth * map.getWidth();
        double height = Settings.tileHeight * map.getHeight();
        for (int i = 0; i <= map.getWidth(); i++) {
            int x = i * Settings.tileWidth;
            engine.drawLine(x, 0, x, height);
        }
        for (int i = 0; i < map.getHeight(); i++) {
            int y = i * Settings.tileHeight;
            engine.drawLine(0, y, width, y);
        }
    }

    public void drawTileNums()
    {
        for (int i = 0; i < map.getWidth(); i++) {
            String x = "" + i;
            for (int j = 0; j < map.getWidth(); j++) {
                String y = "" + j;
                engine.drawString(x + "," + y, i * Settings.tileWidth + 2, j * Settings.tileHeight + 2, -1, tileNum, JGColor.yellow);
            }
        }

    }

    public void drawplayField()
    {
      engine.drawLine(Settings.groundWidth + 1, 0, Settings.groundWidth + 1, Settings.groundHeight);
      engine.drawLine(0, Settings.groundHeight + 1, Settings.groundWidth, Settings.groundHeight + 1);
    }

    public void drawMapState()
    {
       String mapState = Character.toString(map.getMapState());
       engine.drawString(mapState, Settings.groundWidth + 10, 10, -1, font, JGColor.yellow);
    }

    public void drawScoreBoard()
    {
        String spc="       ";
        engine.drawString(spc+spc+"Player" + spc + "Health" + spc + "Coins" + spc + "Score" + spc + "Rank", Settings.groundWidth + 50, 110, -1, font, JGColor.yellow);
        ArrayList<Tank> tempTanks = new ArrayList<Tank>(5);
        for (Tank t : tanks) {
            tempTanks.add(t);
        }
        Collections.sort(tempTanks, new Comparator<Tank>() {

            public int compare(Tank o1, Tank o2) {
                return o2.points - o1.points;
            }
        });
        int r = 1;
        for (Tank t : tempTanks) {
            t.rank = r++;
        }
        //String width = "28";
       // String frmtStr = "";
       // for (int j = 1; j <= 5; j++) {
        //    frmtStr += "%" + j + "$-" + width + "s";
        //}
        int i = 0;
        int spac = 80;
        int ypos = 130;
        for (Tank t : tanks) {
            String health = "" + t.health;
            String coins = "" + t.coins;
            String points = "" + t.points;
            String rank = "" + t.rank;
            engine.drawString(t.name, Settings.groundWidth +100, ypos + i, -1, font, JGColor.yellow);
            engine.drawString(health, Settings.groundWidth + 100 + spac * 1, ypos + i, -1, font, JGColor.yellow);
            engine.drawString(coins, Settings.groundWidth + 100 + spac * 2, ypos + i, -1, font, JGColor.yellow);
            engine.drawString(points, Settings.groundWidth + 100 + spac * 3, ypos + i, -1, font, JGColor.yellow);
            engine.drawString(rank, Settings.groundWidth + 100 + spac * 4, ypos + i, -1, font, JGColor.yellow);
            if (t != map.myTank) {
                engine.drawImage(Settings.groundWidth + 80, ypos + i, t.name + "Icon");
            } else {
                engine.drawImage(Settings.groundWidth + 80, ypos + i, "blueIcon");

            }
            i += 25;
        }



    }

    private int time=0;
    public void drawMessage()
    {
       System.out.println(message);
       engine.drawString(message,Settings.groundWidth + 200 ,35, -1, topic, JGColor.yellow);
        time++;
        if(message.startsWith("Game Started")){
            if(time>240){
                message="";
                time=0;
            }
        }else if(message.startsWith("DEAD")||message.startsWith("PIT FALL")){
        }else{
            time++;
            if(time==24){
                message="";
                time=0;
            }
        }
    }

    public void gridon(boolean val) {
        gridon = val;
    }

    public void showTileNum(boolean val) {
        showTileNum = val;
    }

    public void displayMessage(String msg) {

        message=msg;
        time=0;
    }
}
