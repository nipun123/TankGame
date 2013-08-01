/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comm;

/**
 *
 * @author udara
 */
public class Time {
    private static Time gameTime;                                                // game time object
    private int num1,num2,num3;
    private long sTime,gTime;                                                    //  stated time, toatl game time
    private final int interval=1000;

    public static Time getInstance(){
        if(gameTime==null)
        {
            gameTime=new Time();
        }
        return gameTime;
    }

    public int getRemainTime()
    {
        int remainTime=(int)(gTime+interval-System.currentTimeMillis());         // remained time
        return remainTime;
    }

    public int Steps(){
        return num1;
    }

    public int eventC()
    {
        return num2;
    }

    public int evenL()
    {
        return num3;
    }

    public void increG()
    {
        gTime=System.currentTimeMillis();
        num1++;
    }

    public void increC()
    {
        num2++;
    }

    public void increL()
    {
        num3++;
    }

    void markTime()       
    {
        sTime=System.currentTimeMillis();
    }

    public String print()
    {
        String msg=(System.currentTimeMillis()-sTime)+" G events="+num1;
        System.out.println(msg);
        return msg;
    }

    public long stepTime()
    {
        return gTime-sTime;
    }

    public long startTime()
    {
        return sTime;
    }

    public long gameTime()
    {
        return System.currentTimeMillis()-sTime;
    }

}
