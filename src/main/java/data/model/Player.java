package data.model;

import java.util.List;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Ivo on 06/05/15.
 */
@Data
public class Player {

    private String name;
    private boolean first;

    /**
     * 
     * @param name
     * @param first if player is the first to play
     */
    public Player(@NotNull String name, boolean first) {
        this.first = first;
        this.name = name;
    }

    protected Player() {
    }

    public void makeMove(Game aThis) {
    }

    public void makeMove(Game aThis, List<Move> possibleBeatings) {
    }
}
