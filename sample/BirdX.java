package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.io.PrintStream;
import java.util.ArrayList;

public class BirdX extends flockbase.Bird {
  private int speed = 5;
  private boolean isLeader;
  private double radius = 3.0D;
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

  double distanceBetweenBirds(Bird bird) {
    int x1 = getPos().getX();
    int y1 = getPos().getY();
    int x2 = bird.getPos().getX();
    int y2 = bird.getPos().getY();
    double distance = Math.sqrt((Math.pow((x1 - y1), 2)) + Math.pow((x2 - y2), 2));
    return distance;
  }

  boolean isWithinLimit(ArrayList<Bird> birds) {
    Position position = this.getPos();
    boolean isNear = false;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (distanceBetweenBirds(b) <= radius) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  boolean isWithinTarget(ArrayList<Bird> birds) {
    flockbase.Position position = this.getPos();
    boolean isNear = false;
    int x1 = getTarget().getX();
    int y1 = getTarget().getY();
    for (Bird b : birds) {
      Position pos = b.getPos();
      int x2 = pos.getX();
      int y2 = pos.getY();
      double distance = Math.sqrt((Math.pow((x1 - y1), 2)) + Math.pow((x2 - y2), 2));
      if (distance < radius) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  protected void updatePos() {
    Position currPos = getPos();
    int x = currPos.getX();
    int y = currPos.getY();
    flockbase.Flock flock = this.getFlock();
    ArrayList<flockbase.Bird> flockMembers = flock.getBirds();
    if (!isLeader) {
      Position leaderPosition = flock.getLeader().getPos();
      setTarget(leaderPosition.getX(), leaderPosition.getY());
    }
    int xt = getTarget().getX();
    int yt = getTarget().getY();
    double dy = 0.0D;
    double dx = 0.0D;
    boolean targetState = false;
  // if(distance)
    if ((xt == x) && (yt == y)) {
      if (targetState) {
        dx = 2.0D;
        dy = 2.0D;
        targetState = false;
      } else {
        dx = -2.0D;
        dy = -2.0D;
        targetState = true;
      }
    } else {
      if (isWithinTarget(flockMembers)) {
        dx = 0.0D;
        dy = 0.0D;
      }
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
          double m = (yt - y) / (xt - x);
          if (xt > x) {
            dx = 1.0D;
          } else
            dx = -1.0D;
          dx *= speed;
          dy = m * dx;
        }
      }
    }
    // } else {
    // if(isLeader) {
    // dx = 3.0D;
    // dy = 3.0D;
    // }
    // }
    if ((x + (int) dx <= 1000 && y + (int) dy <= 1000) || (x + (int) dx >= 0 && y + (int) dy >= 0))
      setPos(x + (int) dx, y + (int) dy);
    // else {
    // x = ;
    // y =
    // }

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
