package data.api;

import data.model.Game;

import java.io.File;
import java.io.IOException;

/**
 * Persists and loads the game object.
 * Created by Ivo on 06/05/15.
 */
public interface Dao {

    /**
     * Saves the game in a file.
     * @param game
     * @param file
     * @throws IOException
     */
    void save(Game game, File file) throws IOException;

    /**
     * Loads the game from a file.
     * @param file
     * @return
     * @throws IOException
     */
    Game load(File file) throws IOException;
}
