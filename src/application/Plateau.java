package application;

import java.util.List;

public class Plateau {
    private List<Case> cases;

    public Plateau(List<Case> cases) {
        this.cases = cases;
    }

    public Case getCase(int position) {
        return cases.get(position);
    }

    public int getTaille() {
        return cases.size();
    }
}
