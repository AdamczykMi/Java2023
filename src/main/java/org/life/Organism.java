package org.life;

import java.util.Random;

public class Organism {

  private String name;
  private int energy;
  private Position position;
  private Random random = new Random();

  public Organism(int energy, String name) {
    this.name = name;
    this.energy = energy;
  }

  public void move(Board board) {

    if (this.position == null)
      return;

    int newX = position.getX();
    int newY = position.getY();

    // Decide whether to move vertically or horizontally
    boolean moveVertically = random.nextBoolean();

    if (moveVertically) {
      // Move up or down by 1
      newY += random.nextBoolean() ? 1 : -1;
    } else {
      // Move left or right by 1
      newX += random.nextBoolean() ? 1 : -1;
    }

    if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()) {
      board.moveOrganism(this, newX, newY);
    }
    // TODO: Use the board's moveOrganism method to move the organism DONE
  }

  public void eat(Organism prey) {
    this.energy += prey.getEnergy();
    prey.setEnergy(0); // Set the energy of the eaten organism to 0
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return position;
  }

  public String getName(){
    return name;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }


}

