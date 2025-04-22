package controller;

import javafx.scene.shape.Circle;
import models.BoardGame;
import models.Pawn;

public class PawnController {

	private Pawn pawn;
	private Circle graphicalPawn;

	public PawnController(Pawn pawn) {
	    this.pawn = pawn;
	    this.graphicalPawn = graphicalPawn;
	}


    public void moveForward(int movement, BoardGame boardGame) {
        pawn.setPosition(pawn.getPosition() + movement);
        if (pawn.getPosition() >= boardGame.getSize()) {
            pawn.setPosition(boardGame.getSize() - 1);
        }
    }
}
