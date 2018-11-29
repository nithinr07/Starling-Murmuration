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
    for(Bird b : getFlock().getBirds()) {
      if(b != this) {
        if((Math.abs(b.getPos().getX() - getPos().getX()) < 50) && (Math.abs(b.getPos().getY() - getPos().getY()) < 50)) {
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
    for(Bird b : birds) {
      if(b != this) {
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
    for(Bird b : birds) {
      if(b != this) {
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
    int xt = getTarget().getX();
    int yt = getTarget().getY();
    double dy = 0.0D;
    double dx = 0.0D;
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
            dx *= speed.getX(); 
            dy = m * dx;
          }
        }
      }
      Position pos1, pos2, pos3;
      pos1 = cohesion();
      pos2 = keepDistance();
      pos3 = matchVelocity();
      
      double X = speed.getX() + pos3.getX();
      double Y = speed.getY() + pos3.getY();

      setSpeed((int)X, (int)Y);
      
      double Dx = dx + pos1.getX() + pos2.getX() + pos3.getX();
      double Dy = dy + pos1.getY() + pos2.getY() + pos3.getY();
      
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