package controller.logic.processing.rules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controller.logic.BoardHelper;
import data.api.GameRule;
import data.model.Move;

/**
 * Created by Ivo on 06/05/15.
 */
@Singleton
public class MoveDistanceRestrictionGameRule implements GameRule {

    private final BoardHelper beatPossibilityChecker;
    private String message;

    @Inject
    public MoveDistanceRestrictionGameRule(BoardHelper beatPossibilityChecker) {
        this.beatPossibilityChecker = beatPossibilityChecker;
    }

    @Override
    public boolean verify(Move move) {
        int deltaRow = Math.abs(move.getSourceField().getRowIndex() - move.getDestinationField().getRowIndex());
        int deltaColumn = Math.abs(move.getSourceField().getColumnIndex() - move.getDestinationField().getColumnIndex());
        if (deltaRow == 0 || deltaColumn == 0) {
            message = "Ruchy w pionie i poziomie są niedozwolone!";
            return false;
        }
        boolean beatingMove = beatPossibilityChecker.checkIfBeatingMove(move);
        if (!beatingMove && !(deltaRow == 1 && deltaColumn == 1)) {
            message = "Można ruszyć się tylko o jedno pole!";
            return false;
        } else if (beatingMove && !(deltaRow == 2 && deltaColumn == 2)) {
            message = "Ruch bijący ma zasięg dwóch pól!";
            return false;
        }
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
