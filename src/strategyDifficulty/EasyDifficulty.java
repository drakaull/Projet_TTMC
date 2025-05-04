package strategyDifficulty;

public class EasyDifficulty implements DifficultyStrategy{
	@Override
    public int getTimeLimitInSeconds() {
        return 30;
    }
}
