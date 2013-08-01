/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author udara
 */
public abstract class GameObject {
                             
    public int x, y;
    public char id;
    private Map gameMap;
    private GameObject[][] data;

    public GameObject() {
        gameMap = Map.getInstance();
        data = gameMap.getDetails();

    }

    public GameObject up() {                        // Returns the Object in up
        if (y == 0) {
            return null;
        }
        return data[x][y - 1];
    }

    public GameObject down() {
        if (y == gameMap.getHeight()-1) {          // Return the Object in down
            return null;
        }
        return data[x][y + 1];
    }

    public GameObject left()                      // Return the Object in left
    {
        if(x==0)
        {
            return null;
        }
        return data[x-1][y];
    }

    public GameObject right()                     // Return th Object in right
    {
        if(x==gameMap.getWidth()-1)
        {
            return null;
        }
        return data[x+1][y];
    }
}
