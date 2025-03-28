package application;

import java.util.List;

import java.util.List;
import java.util.Arrays;

public class Plateau {
    private final List<Case> cases = Arrays.asList(
        new Case("Start", 0, 0),
        new Case("Informatics", 1, 0),
        new Case("Education", 2, 0),
        new Case("Entertainment", 2, 1),
        new Case("Random", 1, 1),
        new Case("Improbable", 0, 1),
        new Case("Informatics", 0, 2),
        new Case("Education", 1, 2),
        new Case("Entertainment", 2, 2),
        new Case("Random", 3, 2),
        new Case("End", 3, 3)
    );

    public Case getCase(int index) {
        if (index < 0 || index >= cases.size()) {
            throw new IllegalArgumentException("Position hors limites.");
        }
        return cases.get(index);
    }

    public int getTaille() {
        return cases.size();
    }
}
