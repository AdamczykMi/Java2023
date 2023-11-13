package org.life;

public class Board {

    private int width;
    private int height;
    private Organism[][] organisms;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new Organism[width][height];
    }

    public void addOrganism(Organism organism, int x, int y) {
        if (organisms[x][y] == null) {
            organisms[x][y] = organism;
            organism.setPosition(new Position(x, y));
        } else {
            System.out.println("Position already occupied!");
        }
    }

    public void moveOrganism(Organism organism, int newX, int newY) {
        // TODO implement that one organism eats the other DONE
        if (newX >= 0 && newX < width && newY >= 0 && newY < height && organisms[newX][newY] == null) {
            System.out.println(organism.getName() + " moved from (" + organism.getPosition().getX() + ", " + organism.getPosition().getY() + ") to (" + newX + ", " + newY + ")");
            organisms[organism.getPosition().getX()][organism.getPosition().getY()] = null;
            organisms[newX][newY] = organism;
            organism.setPosition(new Position(newX, newY));

        } else if (newX >= 0 && newX < width && newY >= 0 && newY < height && organisms[newX][newY] != null) {
            Organism existingOrganism = organisms[newX][newY];
            if (existingOrganism != organism && existingOrganism.getPosition() != null) {
                System.out.println(organism.getName() + " moved from (" + organism.getPosition().getX() + ", " + organism.getPosition().getY() + ") to (" + newX + ", " + newY + ")");
                System.out.println(organism.getName() + " at (" + newX + ", " + newY + ") consumed " + existingOrganism.getName() + " and took all of his energy: " + existingOrganism.getEnergy());
                organism.setPosition(new Position(newX, newY));
                organism.eat(existingOrganism);
                organisms[existingOrganism.getPosition().getX()][existingOrganism.getPosition().getY()] = null;
                existingOrganism.setPosition(null); // Remove the eaten organism from the board
            }
        } else {
            System.out.println("Invalid move!");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Organism getOrganismAt(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return organisms[x][y];
        }
        return null; // Return null if the coordinates are out of bounds
    }
}
