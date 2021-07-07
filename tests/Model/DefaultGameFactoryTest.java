package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.TargetReader;
import Model.DefaultGameFactory;
import Model.GameFactory;

class DefaultGameFactoryTest {

	@Test
	void getGameCreateNewGame() {
		TargetReader reader = Mockito.mock(TargetReader.class);
		GameFactory factory = new DefaultGameFactory();
		assertNotNull(factory.getGame(reader));
	}

}
