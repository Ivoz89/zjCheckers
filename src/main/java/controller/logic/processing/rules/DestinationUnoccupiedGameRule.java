package controller.logic.processing.rules;

import com.google.inject.Singleton;
import data.api.GameRule;
import data.model.Move;

/**
 * Checks if destination field is occupied
 * Created by Ivo on 06/05/15.
 */
@Singleton
public class DestinationUnoccupiedGameRule implements GameRule {

    private static final String message = "To pole jest zajęte";

    @Override
    public boolean verify(Move move) {
        return move.getDestinationField().isEmpty();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
