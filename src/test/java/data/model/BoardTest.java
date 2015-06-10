package data.model;

import com.google.inject.Guice;
import com.google.inject.Inject;
import data.api.BoardPopulator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class BoardTest {

    @Before
    public void setUp() {
        Guice.createInjector(new DataTestModule()).injectMembers(this);
    }

    @Inject
    private BoardPopulator populator;

    @Test
    @Parameters
    public void shouldThrowInvalidArgumentExceptionWhenGivenIncorrectSize(int heights, int width) {
        try {
            Board board = new Board(heights, width, populator);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void shouldHaveAllFieldsInitialized() {
        IntStream.range(Board.MIN_ROW_COUNT, Board.MAX_ROW_COUNT).forEach(rowCount ->
                IntStream.range(Board.MIN_COLUMN_COUNT, Board.MAX_COLUMN_COUNT).forEach(columnCount -> {
                            Board board = new Board(rowCount, columnCount, populator);
                            assertEquals(rowCount,board.getFields().size());
                            assertEquals(columnCount, board.getFields().get(0).size());
                            board.getFields().forEach(field -> assertNotNull(field));
                        }
                ));
    }

    public Object[] parametersForShouldThrowInvalidArgumentExceptionWhenGivenIncorrectSize() {
        return $(
                $(0, 0),
                $(Board.MIN_ROW_COUNT - 1, Board.MIN_COLUMN_COUNT),
                $(Board.MIN_ROW_COUNT, Board.MIN_COLUMN_COUNT - 1),
                $(Board.MAX_ROW_COUNT, Board.MAX_COLUMN_COUNT + 1),
                $(Board.MAX_ROW_COUNT + 1, Board.MAX_COLUMN_COUNT),
                $(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

}