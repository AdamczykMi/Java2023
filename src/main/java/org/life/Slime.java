package org.life;

import java.util.Random;

public class Slime extends Organism {

    public Slime(int energy, String name) {
        super(energy, name);
    }


    private Position position;
    private Random random = new Random();

    @Override
    public void move(Board board) {
        if (this.position == null)
            return;

        int newX = position.getX();
        int newY = position.getY();

        boolean moveVertically = random.nextBoolean();

        if (moveVertically) {
            // Move up or down by 2
            if (position.getY() >= 2 && position.getY() <= board.getHeight() - 3) {
                newY += random.nextBoolean() ? 2 : -2;
            } else if (position.getY() < 2) {
                newY += 2;
            } else if (position.getY() > board.getHeight() - 3) {
                newY += -2;
            }
        } else {
            if (position.getX() >= 2 && position.getX() <= board.getWidth() - 3) {
                newX += random.nextBoolean() ? 2 : -2;
            } else if (position.getX() < 2) {
                newX += 2;
            } else if (position.getX() > board.getWidth() - 3) {
                newX += -2;
            }
        }

        int sightRadius = 2;
        for (int y = Math.max(0, newY - sightRadius); y <= Math.min(board.getHeight() - 1, newY + sightRadius); y++) {
            for (int x = Math.max(0, newX - sightRadius); x <= Math.min(board.getWidth() - 1, newX + sightRadius); x++) {
                Organism organism = board.getOrganismAt(x, y);
                if (organism != null && organism != this && organism.getEnergy() != 0) {
                    System.out.println(this.getName() + " detected organism " + organism.getName() + " at position (" + x + ", " + y + ")" + " which has: " + organism.getEnergy() + " energy");
                }
            }
        }

        board.moveOrganism(this, newX, newY);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void eat(Organism prey) {
        super.eat(prey);
    }
}
