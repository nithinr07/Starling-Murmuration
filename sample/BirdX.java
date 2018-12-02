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

  boolean isWithinTarget() {
    boolean isNear = false;
    int x1 = getTarget().getX();
    int y1 = getTarget().getY();
    Position pos = getPos();
    int x2 = pos.getX();
    int y2 = pos.getY();
    double distance = Math.sqrt((Math.pow((x1 - x2), 2)) + Math.pow((y1 - y2), 2));
    if (distance < radius) {
      isNear = true;
    }
    return isNear;
  }

  public Position keepDistance() {
    Position newPosition = new Position();
    newPosition.setPos(0, 0);
    int x = 0;
    int y = 0;
    for (Bird b : getFlock().getBirds()) {
      if (b != this) {
        if ((Math.abs(b.getPos().getX() - getPos().getX()) < 50)
            && (Math.abs(b.getPos().getY() - getPos().getY()) < 50)) {
          x = x - (b.getPos().getX() - getPos().getX());
          y = y - (b.getPos().getY() - getPos().getY());
        }
      }
    }
    newPosition.setPos(x, y);
    return newPosition;
  }

  public Position cohesion() {
    Position newPosition = new Position();
    newPosition.setPos(0, 0);
    int x = 0;
    int y = 0;
    ArrayList<Bird> birds = getFlock().getBirds();
    for (Bird b : birds) {
      if (b != this) {
        x = x + b.getPos().getX();
        y = y + b.getPos().getY();
      }
    }
    x = x / (birds.size() - 1);
    y = y / (birds.size() - 1);
    x = (x - getPos().getX()) / 100;
    y = (y - getPos().getY()) / 100;
    newPosition.setPos(x, y);
    return newPosition;
  }

  public Position matchVelocity() {
    Position newPosition = new Position();
    newPosition.setPos(0, 0);
    int x = 0;
    int y = 0;
    ArrayList<Bird> birds = getFlock().getBirds();
    for (Bird b : birds) {
      if (b != this) {
        x = x + b.getSpeed().getX();
        y = y + b.getSpeed().getY();
      }
    }
    x = x / (birds.size() - 1);
    y = y / (birds.size() - 1);
    x = (x - getSpeed().getX()) / 8;
    y = (y - getSpeed().getY()) / 8;
    newPosition.setPos(x, y);
    return newPosition;
  }

  protected void updatePos() {
    Position currPos = getPos();
    int x = currPos.getX();
    int y = currPos.getY();
    Flock flock = this.getFlock();
    if (!isLeader) {
      Position leaderPosition = flock.getLeader().getPos();
      setTarget(leaderPosition.getX(), leaderPosition.getY());
    }
    int xTarget = getTarget().getX();
    int yTarget = getTarget().getY();
    double dy = 0.0;
    double dx = 0.0;
    if (isWithinTarget()) {
      dx = 0.0;
      dy = 0.0;
    } else {
      if (xTarget == x) {
        if (yTarget > y) {
          dy = 1.0;
        } else
          dy = -1.0;
        dx = 0.0;
      } else {
        if (yTarget == y) {
          if (xTarget > x) {
            dx = 1.0;
          } else
            dx = -1.0;
          dy = 0.0;
        } else {
          double slope = (double) (yTarget - y) / (xTarget - x);
          if (xTarget > x) {
            dx = 1.0;
          } else
            dx = -1.0;
          if (slope > 10.0 || slope < -10.0) {
            if (slope > 0)
              slope = 10.0D;
            else
              slope = -10.0D;
          }
          dx *= speed.getX();
          dy = slope * dx;
          if(dy > 100.0) {
            dy = dy / 10.0;
          }
        }
      }
    }
    Position pos1, pos2, pos3;
    // boids algorithm //
    pos1 = cohesion();
    pos2 = keepDistance();
    pos3 = matchVelocity();
    
    setSpeed(speed.getX() + pos3.getX(), speed.getY() + pos3.getY());

    double Dx = dx + pos1.getX() + pos2.getX() + pos3.getX();
    double Dy = dy + pos1.getY() + pos2.getY() + pos3.getY();

    if (((x + (int) Dx) < 1000 || (y + (int) Dy) < 1000) || ((x + (int) Dx) > 0 || (y + (int) Dy) > 0)) {
      setPos(x + (int) Dx, y + (int) Dy);
    } else {
      setPos(x, y);
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