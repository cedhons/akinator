package Model;

import Data.TargetReader;

public interface GameFactory {
	Game getGame(TargetReader reader);
}
