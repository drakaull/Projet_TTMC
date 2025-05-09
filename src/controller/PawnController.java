package controller;

import javafx.scene.shape.Circle;
import models.BoardGame;
import models.Pawn;

public class PawnController {

	private Pawn pawn; // The logical representation of the pawn
	private Circle graphicalPawn; // Graphical JavaFX representation (currently unused)

	public PawnController(Pawn pawn) {
	    this.pawn = pawn;
	}

    // Moves the pawn forward by a number of cells; ensures it does not go beyond the board size
    public void moveForward(int movement, BoardGame boardGame) {
        pawn.setPosition(pawn.getPosition() + movement);
        if (pawn.getPosition() >= boardGame.getSize()) {
            pawn.setPosition(boardGame.getSize() - 1); // Clamp position to final cell
        }
    }
}
