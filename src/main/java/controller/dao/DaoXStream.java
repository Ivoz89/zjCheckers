package controller.dao;

import com.google.common.io.Files;
import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;
import data.api.Dao;
import data.model.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Serializes Game to xml 
 */
@Singleton
public class DaoXStream implements Dao {

    private final XStream xStream;

    public DaoXStream() {
        this.xStream = new XStream();
    }

    @Override
    public void save(Game game, File file) throws IOException {
        writeStringToFile(file, xStream.toXML(game));
    }

    @Override
    public Game load(File file) throws IOException {
        return (Game) xStream.fromXML(readStringFromFile(file));
    }

    private void writeStringToFile(File file, String content) throws FileNotFoundException, IOException {
        try (BufferedWriter writer = Files.newWriter(file, Charset.forName("UTF-8"))) {
            writer.append(content);
        }
    }

    private String readStringFromFile(File file) throws IOException {
        return Files.toString(file, Charset.forName("UTF-8"));
    }
}
