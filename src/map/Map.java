/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import comm.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author udara
 */
public class Map {

    private static Map gameMap=new Map();                                // Singlton map
    private GameObject[][] details;                                      // game objects 2D array
    private int height, width;                                           // map height and width
    private char mapState;                                               // map state ,like initilized
    private char[][] simMap;                                             // 2D array of game object ids
    private char[][] simMapPre;
    private List<Tank> tanks;                                            // List of Tanks
    private List<Coin> coins;                                            // List of coins
    private List<LifePack> lifePack;                                     // List of Lifepacks
    private List<Brick> bricks;                                          // List of Bricks
    private List<Water> water;                                           // List of water cells
    private List<Stone> stones;                                          // List of stones
    private List<Bullet> bullets;                                        // List of bullets
    private int numTanks;                                                // Number of tanks
    private int ourNum;                                                  // Our tank number
    private List<Listner> listners;                                      // List of map Listners
    private boolean initialize;                                          // Map initialize status
    private String ourName;                                              // Our Tank name
    private Time gTime;
    public Tank myTank;                                                  // our tank

    public Map() {
    }

    public static Map getInstance() {                                    // implement singleton pattern
        if (gameMap == null) {
            gameMap = new Map();
        }
        return gameMap;
    }


    public void regisMapListner(Listner ml) {                            // Register a new napListner
        listners.add(ml);
    }

    public char[][] getsimMap() {                                        // Get the 2D game object id array

        return simMap;
    }

    public void notifyListner() {                                        // notify the Listner on map Change
        for (Listner l : listners) {
            l.onStateChange(gameMap);
        }
    }

    public void initialize(int width, int height) {                      // initialize map give the map width and height
        if (initialize) {
            return;
        }
        details = new GameObject[width][height];                         // create new game object array
        simMap = new char[width][height];                                // create new game object id array
        this.width = width;
        this.height = height;
        

        for (int i = 0; i < details.length; i++) {
            for (int j = 0; j < details[i].length; j++) {
                Ground g = new Ground(i, j);                             // Create Ground object to cover whole map
                details[i][j] = g;
            }
        }
        gTime = Time.getInstance();

        for (int i = 0; i < simMap.length; i++) {
            for (int j = 0; j < simMap[i].length; j++) {
                simMap[i][j] = 'N';                                      // assign N to gameObject ids
            }
            
        }
        for (char[] col : simMap) {
            for (char cel : col) {
                cel = 'N';
            }
            
        }

        tanks = Collections.synchronizedList(new TankList(5));                          // Create a syncronized list from TankListWrap to manage the consistancy of tanklist by managing syncronize access
        coins = Collections.synchronizedList(new GameObjectList<Coin>(10));             // Create a syncronized list from GameObjectListWrap to manage syncronize access to game object lists
        lifePack = Collections.synchronizedList(new GameObjectList<LifePack>(10));
        bricks = Collections.synchronizedList(new GameObjectList<Brick>(5));
        stones = Collections.synchronizedList(new GameObjectList<Stone>());
        water = Collections.synchronizedList(new GameObjectList<Water>());
        bullets = new ArrayList<Bullet>();                                               // crate a bullet list
        listners = new ArrayList<Listner>();                                             // create a maplistner list

        for (GameObject[] col : details) {
            for (GameObject cel : col) {
                System.out.print(cel.id + " " + cel.x + " " + cel.y + ": ");
            }
            System.out.println();
        }

        System.out.print("Map is initialized");

    }

    public int getNumTanks() {                                               // get Num of tanks
        return numTanks;
    }

    public char getMapState()                                               // get current map state
    {
        return mapState;
    }

    public String getOurName() {                                           //  get oor tank name
        return ourName;
    }

    public int  getOurNUm() {                                             // get out tank number
        return ourNum;
    }

    public void setNumTanks(int numTanks) {                               // set the tanknum
        this.numTanks = numTanks;
    }

    public void setourNUm(int ourNum) {                                   // set Our  umber
        this.ourNum = ourNum;
    }

    public void setOurName(String name) {                                 // set our tank name
        this.ourName = name;
    }

    /*
     * states of map
     * N:no data state
     * S:start state
     * I: intialised state
     * G: going on state
     * C: coins available state
     * L:life packs available state
     * X: bothlife packs and coins available
     */
    public void setState(char mapState) {                               // set map state and call updatemap methos
        this.mapState = mapState;                                       // any state change notify the listner
        if (mapState == 'G') {
            simMapPre = simMap;                                         // keep the current map
            updateMap();
        }
        notifyListner();
    }

    private boolean checkCollision(char gameobject) {                 // chaeck for the next object will cause a collision
        if (gameobject == 'B' || gameobject == 'T' || gameobject == 's') {
            return true;
        } else {
            return false;
        }
    }

    private void updateMap() {                                         // on state change call to update map
        long now = System.currentTimeMillis();
        synchronized (coins) {
            Iterator<Coin> i = coins.iterator();
            while (i.hasNext()) {
                Coin c = i.next();
                c.reTime = c.lifeTime - (now - c.creAt);               // calculate remain lifetime
                c.reStep = c.steps - (gTime.Steps() - c.creStep);
                if (c.reStep <= 0) {
                    i.remove();
                    System.out.println(c + " removed");
                    notifyCoinVanish(c);
                }
                if (mapState == 'G') {                                 //  if mapState id G and coinpack life is remain
                    if (c.reTime > 0) {
                        for (Tank t : tanks) {
                            if (c.x == t.x && c.y == t.y) {
                                if (c.reStep > 0) {
                                    i.remove();
                                }
                                c.reStep = 0;
                                notifyCoinPick(c);

                            }
                        }
                    }
                }
            }
        }

        synchronized (lifePack) {
            Iterator<LifePack> i = lifePack.iterator();
            while (i.hasNext()) {
                LifePack l = i.next();
                l.reTime = l.lifTime - (now - l.creAt);
                l.reStep = l.steps - (gTime.Steps() - l.creStep);
                if (l.reStep <= 0) {
                    i.remove();
                    System.out.println(l + " removed");
                    notifyLifePackVanish(l);
                }
                if (mapState == 'G') {                                              //
                    if (l.reTime > 0) {
                        for (Tank t : tanks) {
                            if (l.x == t.x && l.y == t.y) {
                                if (l.reStep > 0) {
                                    i.remove();
                                }
                                l.reStep = 0;
                                notifyLifePackPick(l);
                            }
                        }
                    }
                }
            }
        }

        if (mapState == 'G') {
            for (Tank t : tanks) {
                if (t.shoot) {
                    Bullet b = new Bullet();
                    b.x = t.x;
                    b.y = t.y;
                    b.dir = t.dir;
                    b.shotby = t;
                    b.sorcx = t.x;
                    b.sorcy = t.y;
                    b.shotAt = gTime.Steps();
                    bullets.add(b);
                    notifyBulletShoot(b);
                }
            }

            Iterator<Bullet> i = bullets.iterator();
            char obj1, obj2;
            while (i.hasNext()) {
                Bullet b = i.next();
                if (b.shotAt != gTime.Steps()) {
                    int sht;
                    if (b.dir == 'U') {
                        sht = b.y - 3;
                        for (int y = b.y - 1; y >= sht && y > -1; y--) {
                            obj1 = simMap[b.x][y];
                            obj2 = simMapPre[b.x][y];
                            if (checkCollision(obj1) || checkCollision(obj2)) {
                                b.y = y;
                                notifyBulletHit(b);
                                i.remove();
                                break;
                            }
                        }
                        b.y -= 3;
                    } else if (b.dir == 'D') {
                        sht = b.y + 3;
                        for (int y = b.y + 1; y <= sht && y < height; y++) {
                            obj1 = simMap[b.x][y];
                            obj2 = simMapPre[b.x][y];
                            if (checkCollision(obj1) || checkCollision(obj2)) {
                                b.y = y;
                                notifyBulletHit(b);
                                i.remove();
                                break;
                            }
                        }
                        b.y += 3;

                    } else if (b.dir == 'L') {
                        sht = b.x - 3;
                        for (int x = b.x - 1; x >= sht && x > -1; x--) {
                            obj1 = simMap[x][b.y];
                            obj2 = simMapPre[x][b.y];
                            if (checkCollision(obj1) || checkCollision(obj2)) {
                                b.x = x;
                                notifyBulletHit(b);
                                i.remove();
                                break;
                            }
                        }
                        b.x -= 3;
                    } else if (b.dir == 'R') {
                        sht = b.x + 3;
                        for (int x = b.x + 1; x <= sht && x < width; x++) {
                            obj1 = simMap[x][b.y];
                            obj2 = simMapPre[x][b.y];
                            if (checkCollision(obj1) || checkCollision(obj2)) {
                                b.x = x;
                                notifyBulletHit(b);
                                i.remove();
                                break;
                            }
                        }
                        b.x += 3;
                    }
                }
            }
        }

        Iterator<Tank> i = tanks.iterator();
        while (i.hasNext()) {
            Tank t = i.next();
            if (t.health < 0 & t.removed == false) {
                t.removed = true;
                i.remove();
                i.next();
            }
        }
    }

    private void notifyCoinVanish(Coin c) {                              // notify the mapListner on a coin vanish
        for (Listner l : listners) {
            l.onCoinVanish(c);
        }
    }

    private void notifyLifePackVanish(LifePack lp) {                    // notify the listner on a lifepack vanish
        for (Listner l : listners) {
            l.onLifePackVanish(lp);
        }
    }

    private void notifyCoinPick(Coin c) {                              // notify the mapListner on coin pack pic
        for (Listner l : listners) {
            l.onCoinPick(c);
        }
    }

    private void notifyLifePackPick(LifePack lp) {                     // notify the mapListner onLifepack pick
        for (Listner l : listners) {
            l.onLifePackPick(lp);
        }
    }

    private void notifyBulletHit(Bullet bul) {                         //notify the mapListner on bullet hit
        for (Listner l : listners) {
            l.onBulletHit(bul);
        }
    }

    private void notifyBulletShoot(Bullet bul) {                      //notify the mapListner on bullet shoot
        for (Listner l : listners) {
            l.onBulletShoot(bul);
        }
    }

    public List<Tank> getTanks() {                                   // get the tank list
        if(tanks==null)
            System.out.println("tanks are null");
        return tanks;
    }

    public List<Coin> getCoins() {                                   // get the coinpack list
        return coins;
    }

    public List<LifePack> getLifePack() {                            // get the lifepack list
        return lifePack;
    }

    public List<Brick> getBricks() {                                 // get the bricks list
        return bricks;
    }

    public List<Water> getWater() {                                  // get the water list
        return water;
    }

    public List<Stone> getStones() {                                 // get the stone list
        return stones;
    }

    public GameObject[][] getDetails() {                            // get the mapObject @D array
        return details;
    }

    public int getHeight() {                                        // get map height
        return height;
    }

    public int getWidth() {                                         // get map width
        return width;
    }

    public String print() {                                         // Print map details
        String message = "";
        for (int a = 0; a < simMap[0].length; a++) {
            for (int b = 0; b < simMap.length; b++) {
                message += simMap[a][b] + " ";
            }
            message += "\n";
        }
        System.out.println(message);
        return message;
    }

    private class GameObjectList<L extends GameObject> extends ArrayList<L> {           // create GameObjectListWrap such extends from arraylist and keep Objects

        public GameObjectList() {                                                       // constructor if list size not given
        }

        public GameObjectList(int i) {                                                  // Constructor for given list size
            super(i);
        }

        @Override
        public synchronized boolean add(L e) {                                              //  syncronize access to add element to list
            simMap[e.x][e.y] = e.id;
            details[e.x][e.y] = e;
            return super.add(e);
        }

        @Override
        public synchronized void add(int index, L e) {                                      // syncronized access to add element with a given index
            simMap[e.x][e.y] = e.id;
            details[e.x][e.y] = e;
            super.add(e);
        }

        @Override
        public synchronized boolean remove(Object obj) {                                   // syncronized access to remove objects
            GameObject ob = (GameObject) obj;
            if (ob instanceof LifePack || ob instanceof Coin || ob instanceof Tank) {
                simMap[ob.x][ob.y] = 'N';
                Ground g = new Ground(ob.x, ob.y);
                details[ob.x][ob.y] = g;
                return super.remove(ob);
            } else {
                throw new UnsupportedOperationException("remove only supported for lifepack  coins and tanks");
            }
        }

        @Override
        public synchronized L remove(int index) {                                           // syncronized access to remove object when index is given
            GameObject ob = get(index);
            if (ob instanceof LifePack || ob instanceof Coin || ob instanceof Tank) {
                simMap[ob.x][ob.y] = 'N';
                Ground g = new Ground(ob.x, ob.y);
                details[ob.x][ob.y] = g;
                return super.remove(index);
            } else {
                throw new UnsupportedOperationException("remove only supported for lifepack and coins");
            }

        }

        @Override
        public boolean addAll(int index, Collection c) {
            throw new UnsupportedOperationException("adding collection of game objects is not supported");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("You should not clear all game objects in a list");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("You should not clear all game objects in a list");
        }

        public void removeRanege(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException("You should not clear all game objects in a list");
        }
    }

    private class TankList extends GameObjectList<Tank> {                                 // Tanklist extended from Gameobject list

        private TankList(int i) {
            super(i);
        }

        @Override
        public synchronized boolean add(Tank t) {                                                // syncronized access to add tanks
            boolean result = super.add(t);
            if (t.name == gameMap.ourName) {
                simMap[t.x][t.y] = 'V';
            } else {
                simMap[t.x][t.y] = 'T';
            }
            return result;
        }

        @Override
        public synchronized void add(int index, Tank t) {                                       //syncronized access to add a tank when index given
            super.add(index, t);
            if (t.name == gameMap.ourName) {
                simMap[t.x][t.y] = 'V';
            } else {
                simMap[t.x][t.y] = 'T';
            }
        }

        @Override
        public synchronized boolean remove(Object o) {                                         // syncronized access to remove tank from map only replacing with a Ground
            System.out.println("Tank removed from map only, List will continue to keep the tank as the score board needs to display the score");
            Tank t = (Tank) o;
            details[t.x][t.y] = new Ground(t.x, t.y);
            return true;
        }

        @Override
        public synchronized Tank remove(int index) {                                          // syncronized access to remove tank from map only when index given
            System.out.println("Tank removed from map only, List will continue to keep the tank as the score board needs to display the score");
            Tank t = get(index);
            details[t.x][t.y] = new Ground(t.x, t.y);
            return t;
        }
    }
}
