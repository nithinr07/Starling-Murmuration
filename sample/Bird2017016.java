package sample;

import flockbase.Bird;
import flockbase.Position;
import flockbase.Flock;
import java.util.*;
import java.lang.*;

import java.util.ArrayList;

public class Bird2017016 extends flockbase.Bird {
     // works as the min distance they hold keep at all time
    private boolean currleader;
    private String name;
    private int colX = 0;
    private int colY = 0;

    // constructor in abstract class
    public Bird2017016(String name) {
		super();
        setName(name);

	}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void updatePos() {
        // System.out.println(" currpos " + getName()+" "+getPos().getX()+" "+getPos().getY());
        // velocity
        boolean amleader = false;
        double dirX = 0;
        double dirY = 0; 

        // current position
        int X = getPos().getX();
        int Y = getPos().getY();
    
        Position tar;
        double tX;
        double tY;
        // target position

        if(getFlock().getLeader().getName() != getName()) {
            tar = getFlock().getLeader().getPos();
            tX = tar.getX();
            tY = tar.getY();
            System.out.println("sidechick target " + getName()+" "+tar.getX()+" "+tar.getY());
            if(getFlock().getLeader().getTarget().getX() - getFlock().getLeader().getPos().getX() == 0 ) {
                if(getFlock().getLeader().getTarget().getY() - getFlock().getLeader().getPos().getY() == 0) {
                    return;
                }
            }
        }
        else {
            amleader = true;
            tar = getTarget();
            tX = tar.getX();
            tY = tar.getY();
            System.out.println(" leader  target " + getName()+" "+tar.getX()+" "+tar.getY());

        }
       
        // direction
        dirX = (tX-X);
        dirY = (tY-Y);

        // if(dirX < 10 && dirY < 10) {
        //     return;
        // } 

        // collision addition
        dirX += colX;
        dirY += colY;

        colX = 0;
        colY = 0;

        // find direction wrt to all birds in the sky
        for (Bird i : getFlock().getBirds()) {
            int neighbourX;
            int neighbourY;
            if(name != i.getName()) {
                neighbourX = i.getPos().getX();
                neighbourY = i.getPos().getY();
                // if distance bw them is is than the min distance move in opposite direction
                if(Math.abs(neighbourX - X) <= 20) {
                    // add to direction - (their direction);
                    dirX = dirX - (neighbourX - X);
                    colX = colX - (neighbourX - X);
                }

                if(Math.abs(neighbourY - Y) <= 20) {
                    // add to direction - (their direction);
                    dirY = dirY - (neighbourY - Y);
                    colY = colY - (neighbourY - Y);
                }
            }
        } // by the end you would have found the resultant direction. 
        
        // collision direction adds a new component opp. to the earlier collision 

        // update speed
        int resultant_velocity_X = 0;
        int resultant_velocity_Y = 0;
        
        if(dirX != 0) {
            int i = 0;
            do {
                resultant_velocity_X  = ((int)dirX + i)%(getMaxSpeed());
                i++;
            }while(resultant_velocity_X == 0);
        }
            
        if(dirY != 0) {
            int i = 0;
            do {
                resultant_velocity_Y  = ((int)dirY + i)%(getMaxSpeed());
                i++;
            }while(resultant_velocity_Y == 0);
        }
            

        System.out.println(" nextposres" + getName() + " " + (X + resultant_velocity_X) + " " + (X +resultant_velocity_Y));

        setPos(X + (int)resultant_velocity_X, Y + (int)resultant_velocity_Y);
        // System.out.println(" nextpos" + getName() + " " + getPos().getX() + " " + getPos().getY());
        

    }


    public void becomeLeader() {
        currleader = true;
    }

    public void retireLead() {     
        currleader = false;
    }

}
