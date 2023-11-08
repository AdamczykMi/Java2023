package org.life;

import java.util.Random;

public class Slime extends Organism {

    private int sightRadius;

    public Slime(int energy, String name, int sightRadius) {
        super(energy, name);
        this.sightRadius = sightRadius;
    }

    private Position position;
    private Random random = new Random();

    @Override
    public void move(Board board) {
        if (this.position == null)
            return;


        int newX = position.getX();
        int newY = position.getY();

        // Decide whether to move vertically or horizontally
        boolean moveVertically = random.nextBoolean();

        if (moveVertically) {
            // Move up or down by 2
            newY += random.nextBoolean() ? 2 : -2;
        } else {
            // Move left or right by 2
            newX += random.nextBoolean() ? 2 : -2;
        }

        if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()) {
            board.moveOrganism(this, newX, newY);
        }

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
