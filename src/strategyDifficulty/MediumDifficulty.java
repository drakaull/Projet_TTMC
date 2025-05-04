package strategyDifficulty;

public class MediumDifficulty implements DifficultyStrategy{
	@Override
    public int getTimeLimitInSeconds() {
        return 20;
    }
}
