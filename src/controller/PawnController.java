package controller;

import models.BoardGame;
import models.Case;
import models.Pawn;

public class PawnController {
	
	private Pawn pawn;
	
	public void moveForward(int movement, BoardGame boardgame) {
        pawn.setPosition(pawn.getPosition()+movement);	//position += movement;
        if (pawn.getPosition() >= boardgame.getSize()) {
            pawn.setPosition(boardgame.getSize() -1);		// position = boardgame.getSize() - 1; 
        }
        
        Case newCase = boardgame.getCase(pawn.getPosition());
        pawn.setX(newCase.getX());			//x = newCase.getX();
        pawn.setY(newCase.getY());			//y = newCase.getY();
    }
}
