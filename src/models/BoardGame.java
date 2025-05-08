package models;

import java.util.List;
import java.util.List;
import java.util.Arrays;

public class BoardGame {
	// Defines the list of all board spaces (cases) with their type and coordinates.
    // this list represents the complete path of the board game
	private final List<Case> cases = Arrays.asList(
        new Case("Start", 150, 720),
        new Case("Education", 150, 570),
        new Case("Informatics", 150, 410),
        new Case("Random", 150, 330),
        new Case("Entertainment", 150, 250),
        new Case("Improbable", 190, 130),
        new Case("Informatics", 360, 130),
        new Case("Entertainment", 470, 170),
        new Case("Random", 470, 260),
        new Case("Improbable", 470, 330),
        new Case("Education", 440, 450),
        new Case("Informatics", 310, 490),
        new Case("Education", 310, 650),
        new Case("Entertainment", 350, 770),
        new Case("Improbable", 510, 770),
        new Case("Informatics", 630, 730),
        new Case("Random", 630, 640),
        new Case("Education", 630, 570),
        new Case("Improbable", 630, 410),
        new Case("Entertainment", 630, 250),
        new Case("Informatics", 670, 130),
        new Case("Improbable", 830, 130),
        new Case("Random", 900, 130),
        new Case("Informatics", 990, 130),
        new Case("Entertainment", 1150, 130),
        new Case("Education", 1270, 170),
        new Case("Improbable", 1270, 320),
        new Case("Random", 1270, 405),
        new Case("Entertainment", 1225, 445),
        new Case("Improbable", 1110, 400),
        new Case("Education", 1070, 285),
        new Case("Informatics", 905, 285),
        new Case("Education", 790, 330),
        new Case("Entertainment", 830, 445),
        new Case("Improbable", 950, 485),
        new Case("Random", 950, 565),
        new Case("Entertainment", 910, 610),
        new Case("Informatics", 790, 645),
        new Case("Entertainment", 830, 770),
        new Case("Education", 990, 770),
        new Case("Improbable", 1105, 730),
        new Case("Informatics", 1150, 610),
        new Case("Random", 1230, 610),
        new Case("Education", 1300, 610),
        new Case("End", 1345, 730)
    );

    // Throws an exception if the index is out of bounds
    public Case getCase(int index) {
        if (index < 0 || index >= cases.size()) {
            throw new IllegalArgumentException("Position out of bounds.");
        }
        return cases.get(index);
    }

    public int getSize() {
        return cases.size();
    }

	public int getTotalCases() {
		  return cases.size();
	}
}
