/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comm;

import java.util.*;
import map.*;
import map.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import eng.Hud;

/**
 *
 * @author udara
 */
public class Interpretor {

    public static final Interpretor interp=new Interpretor();
    Map gameMap;
    int numPlayer;
    String myId;
    Queue<String> msgStore;
    GameObject[][] details;
    char[][] simMap;
    List<Tank> tanks;
    List<Coin> coins;
    List<LifePack> lifePacks;
    List<Brick> bricks;
    List<Stone> stones;
    List<Water> water;
    private Communicator com;
    private Coordinator co;
    private Time gTime;
    private Hud hd;

    private Interpretor() {
        initialize();
    }

    public static Interpretor getInstance() {
        return interp;

    }

    private void initialize() {
        msgStore = new ArrayDeque<String>(1);
        numPlayer = 0;
        gameMap = Map.getInstance();       
        simMap = gameMap.getsimMap();

        details = gameMap.getDetails();
        tanks = gameMap.getTanks();
        coins = gameMap.getCoins();
        lifePacks = gameMap.getLifePack();
        bricks = gameMap.getBricks();
        stones = gameMap.getStones();
        water = gameMap.getWater();
        com = new Communicator();
        co = Coordinator.getInstance();
        gTime = Time.getInstance();
        hd=Hud.getInstance();

    }


    public void inputMessage(String msg) {

        System.out.println(System.currentTimeMillis() - gTime.startTime() + " :" + msg);
        String instructions[] = msg.split("#");
        for (String instruction : instructions) {
            if (instruction.startsWith("G:")) {
                gTime.increG();
                processGMsg(instruction);
                gameMap.setState('G');
            } else if (instruction.startsWith("I:")) {
                co.setJoinSucess(true);
                processIMsg(instruction);
                gameMap.setState('I');
                hd.displayMessage("Game Initialized");
            } else if (instruction.startsWith("S:")) {
                co.setJoinSucess(true);
                processSMsg(instruction);
                gameMap.setState('S');
                hd.displayMessage("Game started");
            } else if (instruction.startsWith("L:")) {
                processLMsg(instruction);
                gTime.increL();
                gameMap.setState('L');
                hd.displayMessage("New life pack appear");
            } else if (instruction.startsWith("C:")) {
                processCMsg(instruction);
                gTime.increC();
                gameMap.setState('C');
                hd.displayMessage("New coin pack appear");
            } else {
                processOtherMsg(instruction);
            }
        }
    }

    public void processGMsg(String instruction) {

        System.out.println("Global message---->"+instruction);
        String[] data = instruction.substring(2).split(":");
        for (int i = 0; i < numPlayer; i++) {
            String[] pd = data[i].split("[;,]");
            int x, y;
            x = Integer.parseInt(pd[1]);
            y = Integer.parseInt(pd[2]);
            char dir='K';
            switch (Integer.parseInt(pd[3])) {
                case 0:
                    dir = 'U';
                    break;
                case 1:
                    dir = 'R';
                    break;
                case 3:
                    dir = 'D';
                    break;
                case 4:
                    dir = 'L';
                    break;
                default:
                    System.out.println("Erronous message");
            }
            boolean shoot;
            switch (Integer.parseInt(pd[4])) {
                case 0:
                    shoot = false;
                    break;
                case 1:
                    shoot = true;
                    break;
                default:
                    throw new RuntimeException("Error in message interpretation");
            }
            int health = Integer.parseInt(pd[5]);
            int coins = Integer.parseInt(pd[6]);
            int points = Integer.parseInt(pd[7]);

            if (pd[0].equals(myId)) {
                int tanknum = Integer.parseInt(myId.substring(1));
                int prex, prey;
                Tank mytank = gameMap.myTank;
                prex = mytank.x;
                prey = mytank.y;
                simMap[prex][prey] = 'N';
                details[prex][prey] = new Ground(prex, prey);
                mytank.x = x;
                mytank.y = y;
                mytank.coins = coins;
                mytank.dir = dir;
                mytank.health = health;
                mytank.points = points;
                mytank.shoot = shoot;
                simMap[x][y] = 'V';
                details[x][y] = mytank;
            } else {
                int tanknum = Integer.parseInt(pd[0].substring(1));
                Tank t = null;
                for (Tank it : tanks) {
                    if (it.name.equals(pd[0])) {
                        t = it;
                    }
                }
                int prex, prey;
                prex = t.x;
                prey = t.y;
                simMap[prex][prey] = 'N';
                details[prex][prey] = new Ground(prex, prey);

                t.x = x;
                t.y = y;
                t.coins = coins;
                t.dir = dir;
                t.health = health;
                t.points = points;
                t.shoot = shoot;

                simMap[x][y] = 'T';
                details[x][y] = t;

            }
        }

        if (numPlayer < data.length - 1) {
            for (int i = 0; i < data.length - 1; i++) {
                String[] pd = data[i].split("[;,]");
                boolean added = false;
                for (Tank t : tanks) {
                    if (pd[0].endsWith(t.name)) {
                        added = true;
                    }
                }
                if (!added) {
                    System.out.println("Additional Tank poped up in Game");
                    int x, y;
                    x = Integer.parseInt(pd[1]);
                    y = Integer.parseInt(pd[2]);

                    char dir;
                    switch (Integer.parseInt(pd[3])) {
                        case 0:
                            dir = 'U';
                            break;
                        case 1:
                            dir = 'R';
                            break;
                        case 3:
                            dir = 'D';
                            break;
                        case 4:
                            dir = 'L';
                            break;
                        default:
                            throw new RuntimeException("Errorness message");
                    }
                    Tank t = new Tank(pd[0], x, y, dir);
                    tanks.add(t);

                }
            }

            String[] bricks = data[data.length - 1].split(";");
            for (String brk : bricks) {
                String[] bd = brk.split(",");
                int x, y, damage;
                x = Integer.parseInt(bd[0]);
                y = Integer.parseInt(bd[1]);
                damage = Integer.parseInt(bd[2]);
                GameObject obj = details[x][y];
                if (obj instanceof Brick) {
                    Brick brick = (Brick) obj;
                    brick.health = 100 - (damage * 25);
                    if (brick.health == 0) {
                        simMap[x][y] = 'N';
                        details[x][y] = new Ground(x, y);
                    }
                }
            }
        }
    }

    public void processIMsg(String instruction) {

        System.out.println("Processing I message");
        String[] pd = instruction.substring(2).split("[:]");
        System.out.println("Instruction--->"+instruction);
        System.out.println("Substring-->"+instruction.substring(2));
        myId = pd[0];
        int tanknum = Integer.parseInt(myId.substring(1));
        gameMap.setourNUm(tanknum);
        gameMap.setOurName(myId);
        String bricks;
        String stone;
        String water;
        for (int i = 1; i < pd.length; i++) {
            if (i == 1) {
                bricks = pd[1];
                System.out.println(bricks);
                objectPosition(bricks, 'B');
            }
            if (i == 2) {
                stone = pd[2];
                System.out.println(stone);
                objectPosition(stone, 'S');
            }
            if (i == 3) {
                water = pd[3];
                System.out.println(water);
                objectPosition(water, 'W');
            }
        }
    }

    public void processSMsg(String instruction) {

        gTime.markTime();
        String[] data = instruction.substring(2).split(":");
        Pattern player = Pattern.compile("[a-zA-Z]+[0-9]+;");
        for (int i = 0; i < data.length; i++) {
            Matcher m = player.matcher(data[i]);
            if (m.find()) {
                numPlayer++;
                String[] pd = data[i].split("[;,]");
                int x, y;
                x = Integer.parseInt(pd[1]);
                y = Integer.parseInt(pd[2]);
                char dir;
                switch (Integer.parseInt(pd[3])) {
                    case 0:
                        dir = 'U';
                        break;
                    case 1:
                        dir = 'R';
                        break;
                    case 3:
                        dir = 'D';
                        break;
                    case 4:
                        dir = 'L';
                        break;
                    default:
                        throw new RuntimeException("Errorness message");
                }
                if (pd[0].equals(myId)) {
                    Tank t = new Tank(pd[0], x, y, dir);
                    t.id = 'V';
                    simMap[x][y] = 'V';
                    details[x][y] = t;
                    int tanknum = Integer.parseInt(pd[0].substring(1));
                    gameMap.myTank = t;
                    tanks.add(tanknum, t);
                    System.out.println("Our tank store in gmap: " + gameMap.myTank);

                } else {
                    Tank t = new Tank(pd[0], x, y, dir);
                    t.id = 'T';
                    simMap[x][y] = 'T';
                    details[x][y] = t;
                    int tanknum = Integer.parseInt(pd[0].substring(1));
                    tanks.add(tanknum, t);
                }
            } else {
                gameMap.setNumTanks(numPlayer);
                if (i == data.length - 3) {
                    objectPosition(data[i], 'B');
                } else if (i == data.length - 2) {
                    objectPosition(data[i], 'S');
                } else if (i == data.length - 1) {
                    objectPosition(data[i], 'W');
                } else {
                    throw new RuntimeException("Error in interpretation msg");
                }
            }
        }
    }

    public void processLMsg(String instruction) {

        String[] pd = instruction.substring(2).split("[,:]");
        int x, y, lifetime;
        x = Integer.parseInt(pd[0]);
        y = Integer.parseInt(pd[1]);
        lifetime = Integer.parseInt(pd[2]);
        LifePack l = new LifePack(x, y, lifetime);
        l.creAt = System.currentTimeMillis();
        l.creStep = gTime.Steps();
        simMap[x][y] = 'L';
        details[x][y] = l;
        lifePacks.add(l);
    }

    public void processCMsg(String instruction) {

        String[] cd = instruction.substring(2).split("[,:]");
        int x, y, value;
        long lifetime;
        x = Integer.parseInt(cd[0]);
        y = Integer.parseInt(cd[1]);
        lifetime = Long.parseLong(cd[2]);
        value = Integer.parseInt(cd[3]);
        Coin c = new Coin(x, y, lifetime, value);
        c.creAt = System.currentTimeMillis();
        c.creStep = gTime.Steps();
        c.steps = (int) Math.floor(((float) lifetime / 1000));
        System.out.println("Coin pile @ " + gTime.gameTime() + "Life Time= " + c.lifeTime + " gtep time= " + gTime.stepTime() + "lifeSteps=" + c.steps);
        simMap[x][y] = 'C';
        details[x][y] = c;
        coins.add(c);

    }

    //message types send by the server
    private final String gameStarted = "GAME_ALREADY_STARTED";
    private final String notStarted = "GAME_NOT_STARTED_YET";
    private final String gameOver = "GAME_HAS_FINISHED";
    private final String playersFull = "PLAYERS_FULL";
    private final String alreadyAdded = "ALREADY_ADDED";
    private final String notPlayer = "NOT_A_VALID_CONTESTANT";
    private final String invalidCell = "INVALID_CELL";
    private final String early = "TOO_QUICK";
    private final String cellOcupied = "CELL_OCCUPIED";
    private final String hitObstacle = "OBSTACLE";
    private final String dead = "DEAD";
    private final String requestError = "REQUEST_ERROR";
    private final String serverError = "SERVER_ERROR";


    public void processOtherMsg(String instruction) {

        if (instruction.equals(gameStarted)) {
            co.setJoinErrorMsg(instruction);
            hd.displayMessage(instruction);
        } else if (instruction.equals(alreadyAdded)) {
            co.setJoinErrorMsg(instruction);
            hd.displayMessage(instruction);
        } else if (instruction.equals(notPlayer)) {
            co.setJoinErrorMsg(instruction);
            hd.displayMessage(instruction);
        } else if (instruction.equals(serverError)) {
            co.setJoinErrorMsg(instruction);
            hd.displayMessage(instruction);
        } else if (instruction.equals(requestError)) {
            System.out.println(instruction);
            hd.displayMessage(instruction);
        }else if(instruction.equals(invalidCell))
        {
            System.out.println("AI Malfunctioning");
            hd.displayMessage(instruction);
        }else if(instruction.equals(early))
        {
            System.out.println("AI giving messages too quickly");
            hd.displayMessage(instruction);
        }else if(instruction.equals(cellOcupied))
        {
            System.out.println(instruction);
            hd.displayMessage(instruction);
        }else if(instruction.equals(hitObstacle))
        {
            System.out.println(instruction);
            hd.displayMessage(instruction);
        }else if(instruction.equals(dead))
        {
            System.out.println(instruction);
            hd.displayMessage(instruction);
        }
    }

    public void objectPosition(String posi, char id) {
        String[] coord = posi.split(";");
        for (String c : coord) {
            String[] location = c.split(",");
            int x, y;
            x = Integer.parseInt(location[0]);
            y = Integer.parseInt(location[1]);

            System.out.print(simMap[x][y]);
            simMap[x][y] = id;
            GameObject obj = null;
            if (id == 'B') {
                obj = new Brick();
                obj.x = x;
                obj.y = y;
                bricks.add((Brick) obj);
                System.out.println("Bricks are added");
            } else if (id == 'W') {
                obj = new Water();
                obj.x = x;
                obj.y = y;
                water.add((Water) obj);
            } else if (id == 'S') {
                obj = new Stone();
                obj.x = x;
                obj.y = y;
                stones.add((Stone) obj);
            }

        }
    }


    // Out put messages to server
    public void outMessage(String msg) {
        com.sendMessage(msg);
    }

    public void up() {
        com.sendMessage("UP#");
    }

    public void down() {
        com.sendMessage("DOWN#");
    }

    public void left() {
        com.sendMessage("LEFT#");
    }

    public void right() {
        com.sendMessage("RIGHT#");
        System.out.println("Tank move to right");
    }

    public void shoot() {
        com.sendMessage("SHOOT#");
    }

    public void rejoin() {
        com.sendMessage("JOIN#");
    }
   
}
