/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

/**
 *
 * @author udara
 */
public interface Listner {

    public void onStateChange(Map map);
    public void onLifePackVanish(LifePack lp);
    public void onCoinVanish(Coin co);
    public void onLifePackPick(LifePack lp);
    public void onCoinPick(Coin co);
    public void onBulletHit(Bullet bu);
    public void onBulletShoot(Bullet bu);


}
