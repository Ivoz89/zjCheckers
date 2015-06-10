package data.events;

import data.model.Move;
import data.model.Player;
import java.util.List;
import lombok.Data;

/**
 * Created by Ivo on 07/06/15.
 */
@Data
public class AnotherBeatingPossibleEvent {

    private final List<Move> possibleBeatings;
    private final Player player;
    
    public AnotherBeatingPossibleEvent(List<Move> possibleBeatings, Player player) {
        this.possibleBeatings=possibleBeatings;
        this.player=player;
    }
}
