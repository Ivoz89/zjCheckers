package data.api;

import data.model.Move;

/**
 * Represents a single rule of the game
 * Created by Ivo on 06/05/15.
 */
public interface GameRule {

    /**
     * Checks the move against the rule.
     * @param move
     * @return if the move violates the rule
     */
    boolean verify(Move move);
    
    /**
     * 
     * @return message containing rule voliation details
     */
    public String getMessage();
}
