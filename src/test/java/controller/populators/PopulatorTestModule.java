package controller.populators;

import com.google.inject.AbstractModule;
import data.annotations.*;
import data.api.BoardPopulator;
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
public class PopulatorTestModule extends AbstractModule {

    private final Class populatorClass;

    public PopulatorTestModule(Class populatorClass) {
        this.populatorClass = populatorClass;
    }

    @Override
    protected void configure() {
        bind(BoardPopulator.class).to(populatorClass);
        bind(Player.class).annotatedWith(HumanPlayer.class).to(TestPlayer1.class);
        bind(Player.class).annotatedWith(CPUPlayer.class).to(TestPlayer2.class);
        bindConstant().annotatedWith(RowCount.class).to(Board.CLASSIC_ROW_COUNT);
        bindConstant().annotatedWith(ColumnCount.class).to(Board.CLASSIC_COLUMN_COUNT);
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