package org.life;

import java.util.Random;

public class Dodo extends Organism {
    public Dodo(int energy, String name) {
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


        // Move diagonally by 2
        if (position.getY() >= 2 && position.getY() <= board.getHeight() - 3 && position.getX() >= 2 && position.getX() <= board.getWidth() - 3) {
            newY += random.nextBoolean() ? 2 : -2;
            newX += random.nextBoolean() ? 2 : -2;
        } else if (position.getY() < 2 && position.getX() >= 2 && position.getX() <= board.getWidth() - 3) {
            newY += 2;
            newX += random.nextBoolean() ? 2 : -2;
        } else if (position.getY() > board.getHeight() - 3 && position.getX() >= 2 && position.getX() <= board.getWidth() - 3) {
            newY += -2;
            newX += random.nextBoolean() ? 2 : -2;
        } else if (position.getX() < 2 && position.getY() >= 2 && position.getY() <= board.getHeight() - 3) {
            newY += random.nextBoolean() ? 2 : -2;
            newX += 2;
        } else if (position.getX() > board.getWidth() - 3 && position.getY() >= 2 && position.getY() <= board.getHeight() - 3) {
            newY += random.nextBoolean() ? 2 : -2;
            newX += -2;
        } else if (position.getY() < 2 && position.getX() < 2) {
            newY += 2;
            newX += 2;
        } else if (position.getY() > board.getHeight() - 3 && position.getX() > board.getWidth() - 3) {
            newY -= 2;
            newX -= 2;
        } else if (position.getY() > board.getHeight() - 3 && position.getX() < 2) {
            newY -= 2;
            newX += 2;
        } else if (position.getY() < 2 && position.getX() > board.getWidth() - 3) {
            newY += 2;
            newX -= 2;
        }

        int sightRadius = 1;
        for (int y = Math.max(0, newY - sightRadius); y <= Math.min(board.getHeight() - 1, newY + sightRadius); y++) {
            for (int x = Math.max(0, newX - sightRadius); x <= Math.min(board.getWidth() - 1, newX + sightRadius); x++) {
                Organism organism = board.getOrganismAt(x, y);
                if (organism != null && organism != this) {
                    System.out.println(this.getName() + " wykrył organizm " + organism.getName() + " na pozycji (" + x + ", " + y + ")");
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
}
