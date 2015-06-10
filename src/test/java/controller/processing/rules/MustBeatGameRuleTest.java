package controller.processing.rules;

import controller.logic.processing.rules.MustBeatGameRule;
import data.model.Checker;
import data.model.Board;
import data.model.Field;
import data.model.Player;
import data.model.Move;
import com.google.inject.Guice;
import com.google.inject.Inject;
import controller.processing.ProcessingTestModule;
import data.annotations.HumanPlayer;
import data.annotations.CPUPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MustBeatGameRuleTest {

    @Inject
    MustBeatGameRule gameRule;

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
    public void shouldNotAllowNonBeatingMoveWhenOneIsPossible() throws Exception {
        //given
        Checker movingChecker = new Checker(firstPlayer);
        Checker checkerToBeBeaten = new Checker(secondPlayer);
        Field source = board.getFields().get(5).get(5);
        Field beateeLocation = board.getFields().get(4).get(4);
        Field destination = board.getFields().get(4).get(6);
        //when
        source.setOccupant(movingChecker);
        beateeLocation.setOccupant(checkerToBeBeaten);
        //then
        Move move = new Move(firstPlayer, source, destination);
        assertFalse(gameRule.verify(move));
    }
}