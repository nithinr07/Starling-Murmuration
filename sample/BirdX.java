package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.io.PrintStream;
import java.util.ArrayList;

public class BirdX extends flockbase.Bird {
  private int speed = 10;
  private boolean isLeader;

  public BirdX() {
  }

  public String getName() {
    String name = "";
    if (!isLeader)
      name = "BirdX";
    else
      name = "BirdX(Leader)";
    return name;
  }

  boolean isWithinLimit(Bird bird, ArrayList<Bird> birds) {
    Position position = bird.getPos();
    boolean isNear = false;
    for(Bird b : birds) {
      Position pos = b.getPos();
      if(position.getX() - pos.getX() < 2 && position.getY() - pos.getY() < 2) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  boolean isWithinTargetX(Bird bird, ArrayList<Bird> birds) {
    Position position = bird.getPos();
    boolean isNear = false;
    for(Bird b : birds) {
      Position pos = b.getPos();
      if(position.getX() - pos.getX() < 2) {
        isNear = true;
        break;
      }
    }
    return isNear;    
  }

boolean isWithinTargetY(Bird bird, ArrayList<Bird> birds) {
  Position position = bird.getPos();
  boolean isNear = false;
  for(Bird b : birds) {
    Position pos = b.getPos();
    if(position.getX() - pos.getX() < 2) {
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
    double dy;
    double dx;
    if ((xt == x) && (yt == y)) {
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
    setPos(x + (int) dx, y + (int) dy);
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
