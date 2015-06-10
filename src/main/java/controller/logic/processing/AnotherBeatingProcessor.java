package controller.logic.processing;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import controller.logic.BoardHelper;
import data.api.MoveProcessor;
import data.events.AnotherBeatingPossibleEvent;
import data.exceptions.InvalidMoveException;
import data.model.Move;
import java.util.List;

/**
 * Checks if another beating is possible for the active player
 */
public class AnotherBeatingProcessor implements MoveProcessor{

    private final BoardHelper boardHelper;
    private final EventBus eventBus;

    @Inject
    public AnotherBeatingProcessor(BoardHelper boardHelper, EventBus eventBus) {
        this.boardHelper = boardHelper;
        this.eventBus = eventBus;
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        if(!move.isBeating()) {
            return;
        }
        List<Move> moves =boardHelper.getBeatingMovesForField(move.getDestinationField());
        if(!boardHelper.getBeatingMovesForField(move.getDestinationField()).isEmpty()) {
            eventBus.post(new AnotherBeatingPossibleEvent(moves,move.getPlayer()));
        } 
    }
}
