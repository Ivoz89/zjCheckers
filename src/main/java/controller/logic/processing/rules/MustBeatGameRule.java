package controller.logic.processing.rules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controller.logic.BoardHelper;
import data.api.GameRule;
import data.model.Board;
import data.model.Move;

import java.util.List;

/**
 * Created by Ivo on 06/05/15.
 */
@Singleton
public class MustBeatGameRule implements GameRule {

    private final static String message = "Musisz bić!";
    private final Board board;
    private final BoardHelper beatPossibilityChecker;

    @Inject
    public MustBeatGameRule(Board board, BoardHelper beatPossibilityChecker) {
        this.board = board;
        this.beatPossibilityChecker = beatPossibilityChecker;
    }

    @Override
    public boolean verify(Move move) {
        List<Move> beatingMoves = beatPossibilityChecker.beatingIsPossible(move.getPlayer());
        return beatingMoves.isEmpty() || beatingMoves.contains(move);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
