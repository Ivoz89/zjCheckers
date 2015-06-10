package data.model;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import data.annotations.*;
import data.api.BoardPopulator;
import data.api.MoveProcessor;
import data.exceptions.InvalidMoveException;

import java.util.ArrayList;

/**
 * Created by Ivo on 06/05/15.
 */
public class DataTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BoardPopulator.class).to(TestPopulator.class);
        bind(Player.class).annotatedWith(HumanPlayer.class).to(TestPlayer1.class);
        bind(Player.class).annotatedWith(CPUPlayer.class).to(TestPlayer2.class);
        bindConstant().annotatedWith(RowCount.class).to(Board.CLASSIC_ROW_COUNT);
        bindConstant().annotatedWith(ColumnCount.class).to(Board.CLASSIC_COLUMN_COUNT);
        bind(EventBus.class).toInstance(new EventBus());
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