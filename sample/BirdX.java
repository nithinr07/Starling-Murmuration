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
      name = "IMT2017511";
    else
      name = "IMT2017511(Leader)";
    return name;
  }

  boolean isWithinLimit(ArrayList<Bird> birds) {
    Position position = this.getPos();
    boolean isNear = false;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (position.getX() != 0 && pos.getX() != 0 && position.getX() - pos.getX() < 1 && position.getY() != 0 && pos.getY() != 0 && position.getY() - pos.getY() < 1) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  boolean isWithinTargetX(ArrayList<Bird> birds) {
    flockbase.Position position = this.getPos();
    boolean isNear = false;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (position.getX() - pos.getX() < 2) {
        isNear = true;
        break;
      }
    }
    return isNear;
  }

  boolean isWithinTargetY(ArrayList<Bird> birds) {
    Position position = this.getPos();
    boolean isNear = false;
    for (Bird b : birds) {
      Position pos = b.getPos();
      if (position.getY() - pos.getY() < 2) {
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
    if ((xt == x) && (yt == y)) {
      if (targetState) {
        dx = 2.0D;
        dy = 2.0D;
      } else {
        dx = -2.0D;
        dy = -2.0D;
      }
    } else {
      if(isWithinTargetX(flockMembers) || isWithinTargetY(flockMembers)) {
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
    //   if(isLeader) {
    //     dx = 3.0D;
    //     dy = 3.0D;
    //   }
    // }
    if((x + (int) dx <= 1000 && y + (int) dy <= 1000) || (x + (int) dx >= 0 && y + (int) dy >= 0))
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
