package controller.logic.processing;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.api.MoveProcessor;
import data.exceptions.InvalidMoveException;
import data.model.Move;

/**
 * Does the actual moving of Checker on the Board
 */
@Singleton
public class MoveExecutorProcessor implements MoveProcessor {

    private final QueenConversionProcessor queenMaker;

    @Inject
    public MoveExecutorProcessor(QueenConversionProcessor queenMaker) {
        this.queenMaker = queenMaker;
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        move.getDestinationField().setOccupant(move.getSourceField().removeOccupant());
        queenMaker.process(move);
    }
}
