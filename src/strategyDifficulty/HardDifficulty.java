package strategyDifficulty;

public class HardDifficulty implements DifficultyStrategy{
	@Override
    public int getTimeLimitInSeconds() {
        return 10; //test
    }
}
