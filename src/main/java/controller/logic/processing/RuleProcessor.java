package controller.logic.processing;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controller.logic.BoardHelper;
import controller.logic.processing.rules.DestinationUnoccupiedGameRule;
import controller.logic.processing.rules.MoveDistanceRestrictionGameRule;
import controller.logic.processing.rules.MustBeatGameRule;
import controller.logic.processing.rules.NoMovingBackGameRule;
import data.api.GameRule;
import data.api.MoveProcessor;
import data.exceptions.InvalidMoveException;
import data.model.Move;

import java.util.HashSet;
import java.util.Set;

/**
 * Checks if a Move is compliant with every GameRule
 */
@Singleton
public class RuleProcessor implements MoveProcessor {

    private final Set<GameRule> gameRuleSet;
    private final CheckerRemoverProcessor checkerRemover;

    @Inject
    public RuleProcessor(DestinationUnoccupiedGameRule destinationUnoccupiedGameRule,
            MoveDistanceRestrictionGameRule moveDistanceRestrictionGameRule,
            MustBeatGameRule mustBeatGameRule,
            NoMovingBackGameRule noMovingBackGameRule,
            CheckerRemoverProcessor checkerRemover) {
        gameRuleSet = new HashSet<>();
        gameRuleSet.add(destinationUnoccupiedGameRule);
        gameRuleSet.add(moveDistanceRestrictionGameRule);
        gameRuleSet.add(mustBeatGameRule);
        gameRuleSet.add(noMovingBackGameRule);
        this.checkerRemover = checkerRemover;
    }

    public void checkIfLegal(Move move) throws InvalidMoveException {
        for (GameRule gameRule : gameRuleSet) {
            if (!gameRule.verify(move)) {
                throw new InvalidMoveException(gameRule.getMessage());
            }
        }
    }

    @Override
    public void process(Move move) throws InvalidMoveException {
        checkIfLegal(move);
        checkerRemover.process(move);
    }
}
