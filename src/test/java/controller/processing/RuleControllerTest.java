package controller.processing;

import controller.logic.processing.RuleProcessor;
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

import static org.junit.Assert.fail;

public class RuleControllerTest {
    @Inject
    RuleProcessor ruleController;

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
    public void shouldDetectIllegalMoveAndThrowException() {
        Field source = board.getFields().get(0).get(0);
        source.setOccupant(new Checker(firstPlayer));
        Field destination = board.getFields().get(4).get(4);
        try {
            ruleController.process(new Move(firstPlayer, source, destination));
            fail();
        } catch (InvalidMoveException e) {

        }
    }


}