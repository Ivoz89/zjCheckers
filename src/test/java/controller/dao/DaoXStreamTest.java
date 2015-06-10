package controller.dao;

import com.google.inject.Guice;
import com.google.inject.Inject;
import controller.processing.ProcessingTestModule;
import data.annotations.ColumnCount;
import data.annotations.RowCount;
import data.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DaoXStreamTest {

    @Inject
    @RowCount
    int rowCount;

    @Inject
    @ColumnCount
    int columnCount;

    @Inject
    DaoXStream daoXStream;

    @Inject
    Game game;

    @Before
    public void setUp() {
        Guice.createInjector(new ProcessingTestModule()).injectMembers(this);
    }

    @Test
    public void gameLoadedShouldBeTheSameAsSave() {
        File file = new File(currentDir() + separator() + "test.xml");
        file.deleteOnExit();
        try {
            daoXStream.save(game, file);
        } catch (IOException e) {
            fail();
        }
        Game loadedGame = null;
        try {
            loadedGame = daoXStream.load(file);
        } catch (IOException e) {
            fail();
        }
        //TODO equals for game to compare objects
        assertNotNull(loadedGame);
    }


    private String currentDir() {
        return System.getProperties().getProperty("user.dir");
    }

    private String separator() {
        return System.getProperties().getProperty("path.separator");
    }

}