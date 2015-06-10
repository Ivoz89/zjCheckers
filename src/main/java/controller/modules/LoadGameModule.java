package controller.modules;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.ai.AIPlayer;
import controller.dao.DaoXStream;
import controller.logic.processing.RuleProcessor;
import data.model.Game;
import data.model.Player;

import java.io.File;
import java.io.IOException;

/**
 * Loads the game and injects controller components
 */
public class LoadGameModule extends AbstractModule {

    private final File saveGame;

    public LoadGameModule(File saveGame) {
        this.saveGame=saveGame;
    }

    @Override
    protected void configure() {
        Game game = null;
        try {
            game = new DaoXStream().load(saveGame);
            Player firstPlayer = game.getHumanPlayer();
            AIPlayer secondPlayer = game.getAiPlayer();
            int rowCount = game.getBoard().getRowCount();
            int columnCount = game.getBoard().getColumnCount();
            NewGameModule gameModule = new NewGameModule(firstPlayer,
                    secondPlayer,rowCount,columnCount,0,game.getBoard());
            Injector injector = Guice.createInjector(gameModule);
            EventBus eventBus = gameModule.getEventBus();
            game.setRuleController(injector.getInstance(RuleProcessor.class));
            bind(EventBus.class).toInstance(eventBus);
            game.setEventBus(eventBus);
            eventBus.register(game);
            bind(Game.class).toInstance(game);
        } catch (IOException e) {
            //TODO HANDLING
            e.printStackTrace();
        }
    }
}
