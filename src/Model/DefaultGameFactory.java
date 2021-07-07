package Model;

import java.util.Random;

import Data.TargetReader;

public class DefaultGameFactory implements GameFactory {

	@Override
	public Game getGame(TargetReader reader) {
		return new Selector(new TargetsHandler(reader, new Random()));
	}
}
