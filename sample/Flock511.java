package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.util.ArrayList;

public class Flock511 extends flockbase.Flock {
  private ArrayList<Bird> birds = new ArrayList<>();
  Bird lead = null;

  public Flock511() {
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

  public flockbase.Flock split(int pos) {
    Bird birdAtPos = birds.get(pos);
    Flock flockZ = new Flock511();
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