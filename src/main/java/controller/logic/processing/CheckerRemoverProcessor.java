package controller.logic.processing;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import controller.logic.BoardHelper;
import data.api.MoveProcessor;
import data.events.CheckerBeatenEvent;
import data.exceptions.InvalidMoveException;
import data.model.Board;
import data.model.Checker;
import data.model.Move;

/**
 * Removes the beaten Checker from the Board.
 * Created by Ivo on 07/06/15.
 */
public class CheckerRemoverProcessor implements MoveProcessor {

    private final MoveExecutorProcessor moveExecutor;
    private final Board board;
    private final EventBus eventBus;
    private final BoardHelper boardHelper;

    @Inject
    public CheckerRemoverProcessor(MoveExecutorProcessor moveExecutor, BoardHelper boardHelper, Board board, EventBus eventBus) {
        this.moveExecutor = moveExecutor;
        this.boardHelper = boardHelper;
        this.board = board;
        this.eventBus = eventBus;
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        if(boardHelper.checkIfBeatingMove(move)) {
            move.setBeating(true);
            Checker beaten = boardHelper.getMiddleField(move).removeOccupant();
            eventBus.post(new CheckerBeatenEvent(beaten));
        }
        moveExecutor.process(move);
    }

}
