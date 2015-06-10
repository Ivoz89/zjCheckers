package controller.populators;

import com.google.inject.Guice;
import com.google.inject.Inject;
import data.model.Game;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ClassicPopulatorTest {

    @Inject
    Game game;

    @Before
    public void setUp() {
        Guice.createInjector(new PopulatorTestModule(ClassicPopulator.class)).injectMembers(this);
    }

    @Test
    public void middleTwoRowsShouldBeEmpty() {
        int emptyRowIndex = game.getBoard().getRowCount() / 2 - 1;
        assertTrue(game.getBoard().getFields().get(emptyRowIndex).stream().allMatch(field -> field.isEmpty()));
        assertTrue(game.getBoard().getFields().get(emptyRowIndex + 1).stream().allMatch(field -> field.isEmpty()));
    }
}