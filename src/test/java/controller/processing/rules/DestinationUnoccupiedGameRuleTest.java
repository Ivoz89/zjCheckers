package controller.processing.rules;

import controller.logic.processing.rules.DestinationUnoccupiedGameRule;
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

public class DestinationUnoccupiedGameRuleTest {

    @Inject
    DestinationUnoccupiedGameRule gameRule;

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
    public void shouldNotAllowMoveIntoOccupiedField() throws Exception {
        //given
        Checker checker1 = new Checker(firstPlayer);
        Checker checker2 = new Checker(secondPlayer);
        Field field1 = board.getFields().get(5).get(5);
        Field field2 = board.getFields().get(4).get(4);
        //when
        field1.setOccupant(checker1);
        field2.setOccupant(checker2);
        //then
        Move move = new Move(firstPlayer, field1, field2);
        assertFalse(gameRule.verify(move));
    }
}