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
            if (position.getY() >= 1 && position.getY() <= board.getHeight() - 2) {
                newY += random.nextBoolean() ? 1 : -1;
            } else if (position.getY() < 1) {
                newY += 1;
            } else if (position.getY() > board.getHeight() - 2) {
                newY += -1;
            }
        } else {
            if (position.getX() >= 1 && position.getX() <= board.getWidth() - 2) {
                newX += random.nextBoolean() ? 1 : -1;
            } else if (position.getX() < 1) {
                newX += 1;
            } else if (position.getX() > board.getWidth() - 2) {
                newX += -1;
            }
        }

        board.moveOrganism(this, newX, newY);

        // TODO: Use the board's moveOrganism method to move the organism DONE
    }


    public void eat(Organism prey) {
        this.energy += prey.getEnergy();
        prey.setEnergy(0);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }


}

