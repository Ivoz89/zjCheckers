package controller.logic.processing.rules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controller.logic.BoardHelper;
import data.api.GameRule;
import data.model.Move;
import data.model.Player;

/**
 * Created by Ivo on 06/05/15.
 */
@Singleton
public class NoMovingBackGameRule implements GameRule {

    private final static String message = "Tylko królowa może wykonać ruch do tyłu!";
    private final BoardHelper beatPossibilityChecker;

    @Inject
    public NoMovingBackGameRule(BoardHelper beatPossibilityChecker) {
        this.beatPossibilityChecker = beatPossibilityChecker;
    }

    @Override
    public boolean verify(Move move) {
        if (beatPossibilityChecker.checkIfBeatingMove(move)) {
            return true;
        }
        Player movingPlayer = move.getSourceField().getOccupant().getPlayer();
        if (!move.getSourceField().getOccupant().isQueen()) {
            int sourceRow = move.getSourceField().getRowIndex();
            int destRow = move.getDestinationField().getRowIndex();
            if (movingPlayer.isFirst()) {
                return sourceRow - destRow < 0;
            } else {
                return sourceRow - destRow > 0;
            }
        } 
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
