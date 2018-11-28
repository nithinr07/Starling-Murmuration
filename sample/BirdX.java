package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.io.PrintStream;
import java.util.ArrayList;

public class BirdX extends flockbase.Bird {
  private Position speed = new Position(10, 10);
  private boolean isLeader;
  private double radius = 50.0D;

  public BirdX() {
  }

  public String getName() {
    String name = "";
    if (!isLeader)
      name = "IMT2017511";
    else
      name = "IMT2017511(Leader)";
    return name;
  }

  public Position getSpeed() {
    return speed;
  }

  public void setSpeed(int x, int y) {
    speed.setPos(x, y);
  }

  double distanceBetweenBirds(Bird bird) {
    int x1 = getPos().getX();
    int y1 = getPos().getY();
    int x2 = bird.getPos().getX();
    int y2 = bird.getPos().getY();
    double distance = Math.sqrt((Math.pow((x1 - y1), 2)) + Math.pow((x2 - y2), 2));
    if(isLeader == true)
      System.out.println(distance);
    return distance;
  }

  boolean isWithinLimit(ArrayList<Bird> birds) {
    Position position = this.getPos();
    boolean isNear = false;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (distanceBetweenBirds(b) < radius) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  Bird getBirdWithinLimit(ArrayList<Bird> birds) {
    Position position = this.getPos();
    Bird bird = null;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (distanceBetweenBirds(b) <= radius) {
        bird = b;
        break;
      }
    }
    return bird;
  }

  boolean isWithinTarget() {
    flockbase.Position position = this.getPos();
    boolean isNear = false;
    int x1 = getTarget().getX();
    int y1 = getTarget().getY();
    Position pos = getPos();
    int x2 = pos.getX();
    int y2 = pos.getY();
    double distance = Math.sqrt((Math.pow((x1 - y1), 2)) + Math.pow((x2 - y2), 2));
    if (distance < radius) {
      isNear = true;
    }
    return isNear;
  }

  protected void updatePos() {
    Position currPos = getPos();
    int x = currPos.getX();
    int y = currPos.getY();
    Flock flock = this.getFlock();
    ArrayList<flockbase.Bird> flockMembers = flock.getBirds();
    if (!isLeader) {
      Position leaderPosition = flock.getLeader().getPos();
      setTarget(leaderPosition.getX(), leaderPosition.getY());
    }
    int xt = getTarget().getX();
    int yt = getTarget().getY();
    double dy = 0.0D;
    double dx = 0.0D;
    // boolean targetState = false;
    // Position pos1, pos2, pos3;
    // pos1 = flock.cohesion(this);
    // pos2 = flock.keepDistance(this);
    // pos3 = flock.matchVelocity(this);

    // int dx = pos1.getX() + pos2.getX() + pos3.getX();
    // int dy = pos1.getY() + pos2.getY() + pos3.getY();

    // int X = speed.getX() + dx;
    // int Y = speed.getY() + dy;

    // setSpeed(X, Y);

    // int currX = getPos().getX();
    // int currY = getPos().getY();

    // currX = currX + X;
    // currY = currY + Y;
    // setPos(currX, currY);
    if (isWithinTarget()) {
      dx = 0.0D;
      dy = 0.0D;
    } else {
        if (xt == x) {
          if (yt > y) {
            dy = 1.0D;
          } else
            dy = -1.0D;
          dx = 0.0D;
        } else {
          if (yt == y) {
            if (xt > x) {
              dx = 1.0D;
            } else
              dx = -1.0D;
            dy = 0.0D;
        } else {
          // if(!isWithinLimit(flockMembers)) {
          double m = (double) (yt - y) / (xt - x);
          if (xt > x) {
            dx = 1.0D;
          } else
            dx = -1.0D;
          if(m > 50.0 || m < -50.0) {
            if(m > 0)
              m = 50.0D;
            else
              m = -50.0D; 
          }
          // if(isWithinLimit(flockMembers)) {
          //   Bird bird = getBirdWithinLimit(flockMembers);
          //   Position pos = bird.getPos();
          //   int x1 = getPos().getX();
          //   int y1 = getPos().getY();
          //   int x2 = bird.getPos().getX();
          //   int y2 = bird.getPos().getY();
          //   double distance = Math.sqrt((Math.pow((x1 - y1), 2)) + Math.pow((x2 - y2), 2));
          //   // if(y1 > y2)
          //   //   dy = radius;
          //   // else 
          //   //   dy = -radius;
          //   // System.out.println("-----------------------------------------------------");
          //   // if(x1 > x2)
          //   //   dx = radius;
          //   // else
          //   //   dx = -radius;
          //   dx *= speed; 
          //   dy = m * dx;
          // } else {
            dx *= speed.getX(); 
            dy = m * dx;
          }
        }
      }
    // }

    
      Position pos2;
      // pos1 = flock.cohesion(this);
      pos2 = flock.keepDistance(this);
      // pos2 = flock.matchVelocity(this);
      
      // double X = speed.getX() + pos2.getX();
      // double Y = speed.getY() + pos2.getY();

      // setSpeed((int)X, (int)Y);
      
      double Dx = dx + pos2.getX();
      double Dy = dy + pos2.getY();

    // int currX = getPos().getX();
    // int currY = getPos().getY();

    // currX = currX + X;
    // currY = currY + Y;
    // setPos(currX, currY);      
    if (((x + (int) dx) < 950 && (y + (int) dy) < 950) || ((x + (int) dx) > 50 && (y + (int) dy) > 50)) {
      System.out.println(x+" "+y);
      setPos(x + (int) Dx, y + (int) Dy);
      System.out.println("---------");
    } else {
      setPos(x + (int) dx, y + (int) dy);
    }
  }

  public void becomeLeader() {
    System.out.println("lead " + this);
    isLeader = true;
  }

  public void retireLead() {
    System.out.println("retire " + this);
    isLeader = false;
  }
}
