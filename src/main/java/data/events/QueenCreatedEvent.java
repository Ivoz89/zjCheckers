package data.events;

import data.model.Checker;
import data.model.Player;
import lombok.Data;

/**
 * Created by Ivo on 07/06/15.
 */
@Data
public class QueenCreatedEvent {

    private final Player player;
    private final Checker checker;

    public QueenCreatedEvent(Player player, Checker checker) {
        this.player = player;
        this.checker = checker;
    }
}
