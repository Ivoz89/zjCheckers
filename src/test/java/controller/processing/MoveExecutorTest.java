package controller.processing;

import controller.logic.processing.MoveExecutorProcessor;
import data.model.Checker;
import data.model.Board;
import data.model.Field;
import data.model.Player;
import data.model.Move;
import com.google.inject.Guice;
import com.google.inject.Inject;
import data.annotations.HumanPlayer;
import data.annotations.CPUPlayer;
import data.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveExecutorTest {

    @Inject
    MoveExecutorProcessor moveExecutor;

    @Inject
    Board board;

    @Inject
    @HumanPlayer
    Player firstPlayer;

    @Inject
    @CPUPlayer
    Player secondPlayer;

    @Before
    public void setUp() {
        Guice.createInjector(new ProcessingTestModule()).injectMembers(this);
    }

    @Test
    public void shouldExecuteMove() {
        //given
        Field source = board.getFields().get(1).get(1);
        Field destination = board.getFields().get(2).get(2);
        Checker checker = new Checker(firstPlayer);
        source.setOccupant(checker);
        Move move = new Move(firstPlayer, source, destination);
        //when
        try {
            moveExecutor.process(move);
        } catch (InvalidMoveException ex) {
            fail();
        }
        //then
        assertTrue(destination.getOccupant() == checker);
    }


}