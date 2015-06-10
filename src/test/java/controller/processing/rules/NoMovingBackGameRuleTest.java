package controller.processing.rules;

import controller.logic.processing.rules.NoMovingBackGameRule;
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
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class NoMovingBackGameRuleTest {

    @Inject
    NoMovingBackGameRule noMovingBackGameRule;

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
    @Parameters
    public void shouldDetectIllegalBackwardMove(int sourceRow, int sourceColumn, Player player) throws Exception {
        //given
        Checker checker = new Checker(player);
        Field sourceField = board.getFields().get(sourceRow).get(sourceColumn);
        sourceField.setOccupant(checker);
        int oppositeDirection = player.isFirst() ? 1 : -1;
        Field destination1 = board.getFields().get(sourceRow + oppositeDirection).get(sourceColumn + 1);
        Field destination2 = board.getFields().get(sourceRow + oppositeDirection).get(sourceColumn - 1);
        Move move1 = new Move(player, sourceField, destination1);
        Move move2 = new Move(player, sourceField, destination2);
        assertFalse(noMovingBackGameRule.verify(move1));
        assertFalse(noMovingBackGameRule.verify(move2));
    }

    public Object[] parametersForShouldDetectIllegalBackwardMove() {
        return $(
                $(5, 5, new Player("", true)),
                $(5, 5, new Player("", false)),
                $(7, 3, new Player("", false)),
                $(0, 3, new Player("", true))
        );
    }
}
