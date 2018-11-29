package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.util.ArrayList;

public class FlockY extends flockbase.Flock {
  private ArrayList<Bird> birds = new ArrayList<>();
  Bird lead = null;

  public FlockY() {
  }

  public void addBird(Bird b) {
    birds.add(b);
    b.setFlock(this);
  }

  public void removeBird(Bird b) {
    birds.remove(b);
  }

  public void setLeader(Bird leader) {
    if (lead != null) {
      lead.retireLead();
    }
    lead = leader;
    lead.becomeLeader();
  }

  public ArrayList<Bird> getBirds() {
    return birds;
  }

  public Bird getLeader() {
    return lead;
  }

  public Position cohesion(Bird bird) {
    Position newPosition = new Position();
    newPosition.setPos(0, 0);
    int x = 0;
    int y = 0;
    for(Bird b : birds) {
      if(b != bird) {
        x = x + b.getPos().getX();
        y = y + b.getPos().getY();
      }
    }
    x = x / (birds.size() - 1);
    y = y / (birds.size() - 1);
    x = (x - bird.getPos().getX()) / 100;
    y = (y - bird.getPos().getY()) / 100;
    newPosition.setPos(x, y);
    return newPosition;
  }

  // public Position keepDistance(Bird bird) {
  //   Position newPosition = new Position();
  //   newPosition.setPos(0, 0);
  //   int x = 0;
  //   int y = 0;
  //   for(Bird b : birds) {
  //     if(b != bird) {
  //       if((Math.abs(b.getPos().getX() - bird.getPos().getX()) < 50) && (Math.abs(b.getPos().getY() - bird.getPos().getY()) < 50)) {
  //         x = x - (b.getPos().getX() - bird.getPos().getX());
  //         y = y - (b.getPos().getY() - bird.getPos().getY());
  //       }
  //     }
  //   }
  //   newPosition.setPos(x, y);
  //   return newPosition;
  // }

  public Position matchVelocity(Bird bird) {
    Position newPosition = new Position();
    newPosition.setPos(0, 0);
    int x = 0;
    int y = 0;
    for(Bird b : birds) {
      if(b != bird) {
        x = x + b.getSpeed().getX();
        y = y + b.getSpeed().getY();
      }
    }
    x = x / (birds.size() - 1);
    y = y / (birds.size() - 1);
    x = (x - bird.getSpeed().getX()) / 8;
    y = (y - bird.getSpeed().getY()) / 8;
    newPosition.setPos(x, y);
    return newPosition;
  }

  public flockbase.Flock split(int pos) {
    Bird birdAtPos = birds.get(pos);
    Flock flockZ = new FlockY();
    birdAtPos.becomeLeader();
    flockZ.addBird(birdAtPos);
    flockZ.setLeader(birdAtPos);
    for (int i = 0; i < pos; i++) {
      flockZ.addBird(birds.get(i));
    }
    birds.remove(pos);
    for (int i = 0; i < pos - 1; i++) {
      birds.remove(i);
    }
    return flockZ;
  }

  public void joinFlock(flockbase.Flock f) {
    getLeader().retireLead();
    for (Bird bird : getBirds()) {
      f.addBird(bird);
    }
  }
}
