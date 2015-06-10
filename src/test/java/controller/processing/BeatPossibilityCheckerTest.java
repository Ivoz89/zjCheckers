package controller.processing;

import controller.logic.BoardHelper;
import data.model.Checker;
import data.model.Board;
import data.model.Field;
import data.model.Player;
import data.model.Move;
import com.google.inject.Guice;
import com.google.inject.Inject;
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
public class BeatPossibilityCheckerTest {

    @Inject
    BoardHelper beatPossibilityChecker;

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
    public void shouldDetectBeatingMove(int sourceRow, int sourceColumn, int destRow, int destColumn) throws Exception {
        //given
        Checker checker1 = new Checker(firstPlayer);
        Checker checker2 = new Checker(secondPlayer);
        Field field1 = board.getFields().get(sourceRow).get(sourceColumn);
        Field field2 = board.getFields().get((sourceRow + destRow) / 2).get((sourceColumn + destColumn) / 2);
        Field field3 = board.getFields().get(destRow).get(destColumn);
        //when
        field1.setOccupant(checker1);
        field2.setOccupant(checker2);
        //then
        Move move = new Move(firstPlayer, field1, field3);
        assertTrue(beatPossibilityChecker.checkIfBeatingMove(move));
    }

    @Test
    @Parameters
    public void shouldDetectAllPossibleBeatingMoves(int sourceRow, int sourceColumn) throws Exception {
        //given
        Checker checker1 = new Checker(firstPlayer);
        Checker checker2 = new Checker(secondPlayer);
        Checker checker3 = new Checker(secondPlayer);
        Checker checker4 = new Checker(secondPlayer);
        Checker checker5 = new Checker(secondPlayer);
        Field field1 = board.getFields().get(sourceRow).get(sourceColumn);
        Field field2 = board.getFields().get(sourceRow-1).get(sourceColumn-1);
        Field field3 = board.getFields().get(sourceRow+1).get(sourceColumn+1);
        Field field4 = board.getFields().get(sourceRow-1).get(sourceColumn+1);
        Field field5 = board.getFields().get(sourceRow+1).get(sourceColumn-1);
        field1.setOccupant(checker1);
        field2.setOccupant(checker2);
        field3.setOccupant(checker3);
        field4.setOccupant(checker4);
        field5.setOccupant(checker5);
        assertEquals(4,beatPossibilityChecker.beatingIsPossible(firstPlayer).size());
    }

    public Object[] parametersForShouldDetectBeatingMove() {
        return $(
                $(0, 0, 2, 2),
                $(1, 1, 3, 3),
                $(3, 3, 1, 1),
                $(2, 2, 0, 0),
                $(5, 5, 7, 7),
                $(5, 5, 7, 3),
                $(5, 5, 3, 7),
                $(5, 5, 3, 3)
        );
    }

    public Object[] parametersForShouldDetectAllPossibleBeatingMoves() {
        return $(
                $(5, 5),
                $(4, 4),
                $(5, 4),
                $(4, 5),
                $(3, 3)
        );
    }
}