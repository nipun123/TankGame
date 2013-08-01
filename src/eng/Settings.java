/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eng;

import jgame.JGFont;
import map.*;


/**
 *
 * @author Udara
 */
public class Settings {
   //game configuration
    public static int tileWidth = 35;                                             // width of a map tile
    public static int tileHeight = 35;                                            // height of a map tile
    public static int hudWidth = 15;                                              // extra width goes to hud
    public static int hudHeight = 0;                                              // hight goes to the hud( since hud is same height of the map hudheight=0
    public static double fps = 15;                                                // number of frames per second
    //game data
    private static Map gmap;
    public static int groundWidth; //play field width px
    public static int groundHeight;

    //static init block
    public static void initialize() {
        gmap = Map.getInstance();
        groundWidth = gmap.getWidth() * tileWidth;
        groundHeight = gmap.getHeight() * tileHeight;
    }

    public static void defineMedia(Engine eng) {

        /**
         * B=brick
         * T=Tank
         * X=tank who has shot.
         * S=stone
         * W=water
         * C=Coins
         * L=LifePack
         * V=our tank
         * N=nothing
         */
        // <editor-fold defaultstate="collapsed" desc="define images 30 px">
        if (tileWidth == 30) {
            eng.defineImage("Bullet", "X", 1, "resources/bullet.png", "-");
            eng.defineImage("Stone", "S", 2, "resources/stone.png", "");
            eng.defineImage("Brick", "B", 2, "resources/brick.png", "");
            eng.defineImage("Water", "W", 4, "resources/water.png", "");
            eng.defineImage("Coins", "C", 8, "resources/coins.png", "");
            eng.defineImage("Life", "L", 8, "resources/life.png", "");
            eng.defineImage("Ground", "N", 0, "null", "");

            eng.defineImage("Tank", "T", 1, "resources/tank.png", "-");
            eng.defineImage("OurTank", "V", 2, "resources/ourTank.png", "r");

            eng.defineImage("TankU", "TU", 1, "resources/tank.png", "y");
            eng.defineImage("TankL", "TL", 1, "resources/tank.png", "r");
            eng.defineImage("TankR", "TR", 1, "resources/tank.png", "l");
            eng.defineImage("TankD", "TD", 1, "resources/tank.png", "-");

            eng.defineImage("OurTankU", "VU", 2, "resources/ourTank.png", "y");
            eng.defineImage("OurTankL", "VL", 2, "resources/ourTank.png", "r");
            eng.defineImage("OurTankR", "VR", 2, "resources/ourTank.png", "l");
            eng.defineImage("OurTankD", "VD", 2, "resources/ourTank.png", "-");
            //hud settings

            eng.defineImage("Hud", "H", 0, "resources/hud.png", "");

            //Manual override, arrow images
            eng.defineImage("ArrowUP", "AUP", 0, "resources/arrow.gif", "y");//flip y
            eng.defineImage("ArrowDown", "ADWN", 0, "resources/arrow.gif", "-");
            eng.defineImage("ArrowLeft", "AL", 0, "resources/arrow.gif", "r");//rotate right
            eng.defineImage("ArrowRight", "AR", 0, "resources/arrow.gif", "l");//rotate left

        }// </editor-fold>
        else if (tileWidth == 35) {
            System.out.println(" Define media for tile size " + tileWidth);

            eng.defineMedia("mapResources/gameProps.tbl");

            eng.defineImage("BackGround", "O", 0, "mapResources/back.png", "");
            

            eng.defineImage("Stone", "S", 2, "mapResources/stone35.png", "");
            eng.defineImage("Brick", "B", 2, "mapResources/Brick35.png", "");
            eng.defineImage("Water", "W", 4, "mapResources/water35.png", "");
            eng.defineImage("Coins", "C", 8, "mapResources/Coins35.png", "");
            eng.defineImage("Life", "L", 8, "mapResources/LifePack35.png","");
            eng.defineImage("Ground", "N", 0, "null", "");

            eng.defineImage("Tank", "T", 1, "mapResources/tankRed.png", "-");
            eng.defineImage("TankYellow", "T", 1, "mapResources/tankYellow.png", "-");
            eng.defineImage("TankRed", "T", 1, "mapResources/tankRed.png", "-");
            eng.defineImage("TankPink", "T", 1, "mapResources/tankPink.png", "-");
            eng.defineImage("TankOrange", "T", 1, "mapResources/tankOrange.png", "-");
            eng.defineImage("TankCryon", "T", 1, "mapResources/tankCryon.png", "-");
            eng.defineImage("TankBlue", "T", 1, "mapResources/tankBlue.png", "-");
            eng.defineImage("OurTank", "V", 2, "mapResources/tankBlue.png", "r");

            eng.defineImage("TankU", "TU", 1, "mapResources/tankRed.png", "y");
            eng.defineImage("TankL", "TL", 1, "mapResources/tankRed.png", "r");
            eng.defineImage("TankR", "TR", 1, "mapResources/tankRed.png", "l");
            eng.defineImage("TankD", "TD", 1, "mapResources/tankRed.png", "-");

            /**
             * 0 yellow
             * 1 red
             * 2 pink
             * 3 cryon
             * 4 orange
             */
            String[] colours={"Yellow","Red","Pink","Cryon","Orange"};
            for (int i = 0; i < 5; i++) {
                eng.defineImage("Tank"+i+"U", "TU", 1, "mapResources/tank"+colours[i]+".png", "y");
                eng.defineImage("Tank"+i+"L", "TL", 1, "mapResources/tank"+colours[i]+".png", "r");
                eng.defineImage("Tank"+i+"R", "TR", 1, "mapResources/tank"+colours[i]+".png", "l");
                eng.defineImage("Tank"+i+"D", "TD", 1, "mapResources/tank"+colours[i]+".png", "-");
            }

            eng.defineImage("OurTankU", "VU", 2, "mapResources/tankBlue.png", "y");
            eng.defineImage("OurTankL", "VL", 2, "mapResources/tankBlue.png", "r");
            eng.defineImage("OurTankR", "VR", 2, "mapResources/tankBlue.png", "l");
            eng.defineImage("OurTankD", "VD", 2, "mapResources/tankBlue.png", "-");

            //define hud icons
            for (int i = 0; i < 5; i++) {
                eng.defineImage("P"+i+"Icon", "T", 1, "mapResources/P"+i+".png", "-");
            }
            eng.defineImage("blueIcon", "T", 1, "mapResources/PBlue.png", "-");
            eng.defineImage("Coin", "T", 1, "mapResources/Coin.png", "-");


            //Bullet
            eng.defineImage("BulletU", "X", 1, "mapResources/Bullet35.png", "-");
            eng.defineImage("BulletL", "X", 1, "mapResources/Bullet35.png", "l");
            eng.defineImage("BulletR", "X", 1, "mapResources/Bullet35.png", "r");
            eng.defineImage("BulletD", "X", 1, "mapResources/Bullet35.png", "y");

            //hud settings
            eng.defineImage("Hud", "H", 0, "mapResources/hud35.png", "");

            //Manual override, arrow images
            eng.defineImage("ArrowUP", "AUP", 0, "mapResources/arrow.gif", "y");//flip y
            eng.defineImage("ArrowDown", "ADWN", 0, "mapResources/arrow.gif", "-");
            eng.defineImage("ArrowLeft", "AL", 0, "mapResources/arrow.gif", "r");//rotate right
            eng.defineImage("ArrowRight", "AR", 0, "mapResources/arrow.gif", "l");//rotate left


            eng.setBGImage("BackGround");
            System.out.println("Background Set successfully");
        }

    }
}
