package controller.processing;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import controller.logic.processing.rules.DestinationUnoccupiedGameRule;
import controller.logic.processing.rules.MoveDistanceRestrictionGameRule;
import controller.logic.processing.rules.MustBeatGameRule;
import controller.logic.processing.rules.NoMovingBackGameRule;
import data.annotations.*;
import data.api.BoardPopulator;
import data.api.GameRule;
import data.api.MoveProcessor;
import data.exceptions.InvalidMoveException;
import data.model.Board;
import data.model.Field;
import data.model.Move;
import data.model.Player;

import java.util.ArrayList;

/**
 * Created by Ivo on 06/05/15.
 */
public class ProcessingTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BoardPopulator.class).to(TestPopulator.class);
        bind(Player.class).annotatedWith(HumanPlayer.class).to(TestPlayer1.class);
        bind(Player.class).annotatedWith(CPUPlayer.class).to(TestPlayer2.class);
        bindConstant().annotatedWith(RowCount.class).to(Board.CLASSIC_ROW_COUNT);
        bindConstant().annotatedWith(ColumnCount.class).to(Board.CLASSIC_COLUMN_COUNT);
        Multibinder<GameRule> ruleBinder = Multibinder.newSetBinder(binder(), GameRule.class);
        ruleBinder.addBinding().to(DestinationUnoccupiedGameRule.class);
        ruleBinder.addBinding().to(NoMovingBackGameRule.class);
        ruleBinder.addBinding().to(MustBeatGameRule.class);
        ruleBinder.addBinding().to(MoveDistanceRestrictionGameRule.class);
    }

    public static class TestPlayer1 extends Player {
        public TestPlayer1() {
            super("test1",true);
        }
    }

    public static class TestPlayer2 extends Player {
        public TestPlayer2() {
            super("test2",false);
        }
    }

    public static class TestPopulator implements BoardPopulator {
        @Override
        public void populateBoard(ArrayList<ArrayList<Field>> fields) {

        }
    }

    public static class TestProcessor implements MoveProcessor {
        @Override
        public void process(Move move) throws InvalidMoveException {
            if (move == null) throw new InvalidMoveException();
        }
    }

}