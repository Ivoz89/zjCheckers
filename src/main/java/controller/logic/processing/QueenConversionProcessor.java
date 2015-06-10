package controller.logic.processing;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import controller.ai.AIPlayer;
import data.annotations.HumanPlayer;
import data.annotations.RowCount;
import data.annotations.CPUPlayer;
import data.api.MoveProcessor;
import data.events.QueenCreatedEvent;
import data.exceptions.InvalidMoveException;
import data.model.Field;
import data.model.Move;
import data.model.Player;

/**
 * Converts makes Checker a Queen if applicable
 */
public class QueenConversionProcessor implements MoveProcessor {

    private final GameEndProcessor gameEndChecker;
    private final EventBus eventBus;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final int rowCount;

    @Inject
    public QueenConversionProcessor(GameEndProcessor gameEndChecker, EventBus eventBus, @HumanPlayer Player firstPlayer, @CPUPlayer AIPlayer secondPlayer,
                      @RowCount int rowCount) {
        this.gameEndChecker = gameEndChecker;
        this.eventBus = eventBus;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.rowCount = rowCount;
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        Field destinatioField = move.getDestinationField();
        if (move.getPlayer().equals(firstPlayer)) {
            if (destinatioField.getRowIndex()==rowCount-1) {
                destinatioField.getOccupant().setQueen(true);
                eventBus.post(new QueenCreatedEvent(firstPlayer, destinatioField.getOccupant()));
            }
        } else {
            if (destinatioField.getRowIndex()==0) {
                destinatioField.getOccupant().setQueen(true);
                eventBus.post(new QueenCreatedEvent(secondPlayer, destinatioField.getOccupant()));
            }
        }
        gameEndChecker.process(move);
    }
}
