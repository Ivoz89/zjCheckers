package controller.logic.processing;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import controller.ai.AIPlayer;
import data.annotations.HumanPlayer;
import data.annotations.CPUPlayer;
import data.api.MoveProcessor;
import data.events.GameOverEvent;
import data.exceptions.InvalidMoveException;
import data.model.Board;
import data.model.Move;
import data.model.Player;

/**
 * Created by Ivo on 07/06/15.
 */
public class GameEndProcessor implements MoveProcessor {

    private final AnotherBeatingProcessor anotherBeatingExecutor;
    private final EventBus eventBus;
    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;

    @Inject
    public GameEndProcessor(AnotherBeatingProcessor anotherBeatingExecutor, EventBus eventBus, Board board,
                          @HumanPlayer Player firstPlayer, @CPUPlayer AIPlayer secondPlayer) {
        this.anotherBeatingExecutor = anotherBeatingExecutor;
        this.eventBus = eventBus;
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        if(checkIfLoser(firstPlayer)) {
            eventBus.post(new GameOverEvent(secondPlayer));
        } else if(checkIfLoser(secondPlayer)) {
            eventBus.post(new GameOverEvent(firstPlayer));
        } else {
           anotherBeatingExecutor.process(move);
        }
    }

    private boolean checkIfLoser(Player player) {
        return board.getFieldsAsStream().noneMatch(f -> f.getOccupant() != null && f.getOccupant().getPlayer().equals(player));
    }
}
