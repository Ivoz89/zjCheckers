package data.events;

import data.model.Player;
import lombok.Data;

/**
 * Created by Ivo on 07/06/15.
 */
@Data
public class GameOverEvent {

    private  final Player winner;

    public GameOverEvent(Player winner) {
        this.winner = winner;
    }
}
