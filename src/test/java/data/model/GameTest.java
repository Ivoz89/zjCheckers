package data.model;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import controller.ai.AIPlayer;
import controller.modules.NewGameModule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ivo on 06/05/15.
 */
public class GameTest {

    @Inject
    private Game game;

    @Before
    public void setUp() {
        Guice.createInjector(new DataTestModule()).injectMembers(this);
    }

//    @Test
    public void shouldBeProperlyInitializedAfterInjection() {
        assertNotNull(game.getBoard());
        assertNotNull(game.getHumanPlayer());
        assertNotNull(game.getAiPlayer());
    }
    
    @Test
    public void injectorShouldProperlyCreateGame() {
        NewGameModule newGameModule = new NewGameModule(new Player(), new AIPlayer("test",false),
                5, 5,0);
        Injector injector = Guice.createInjector(newGameModule);
        Game createdGame = injector.getInstance(Game.class);
        assertNotNull(createdGame.getEventBus());
    }
}
