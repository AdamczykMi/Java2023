package org.life;

public class LifeSimulator {

  public static void main(String[] args) {
    Board board = new Board(20, 20);
    /* TODO:
     - Add at least 2 classes that implement the Organism interface.
     - The new classes should possess unique abilities, such as:
        -- Jumping (moving more than 1 step at a time). DONE
        -- Sight (detecting other organisms within a certain radius). DONE
        -- Avoiding illegal moves. DONE
     - Ensure that an Organism doesn't move if it attempts an illegal move. DONE
     - Implement a mechanism where an Organism consumes another (taking all its energy) when it occupies the same space. DONE
     - Run simulation for some time, eg. when there is only one Organism left DONE
     */
    Organism pies = new Organism(500, "Pies");
    Organism kot = new Organism(300, "Kot");
    Organism slime = new Slime(200, "slime1");
    Organism slime2 = new Slime(180, "slime2");
    Organism dodo = new Dodo(150, "dodo1");
    Organism dodo2 = new Dodo(130, "dodo2");
    board.addOrganism(pies, 18, 6);
    board.addOrganism(kot, 5, 14);
    board.addOrganism(slime, 12, 4);
    board.addOrganism(slime2, 16, 16);
    board.addOrganism(dodo, 0, 18);
    board.addOrganism(dodo2, 14, 10);

    for (int i = 0; i < 1000; i++){
      pies.move(board);
      kot.move(board);
      slime.move(board);
      dodo.move(board);
      dodo2.move(board);
      slime2.move(board);
    }

  }
}