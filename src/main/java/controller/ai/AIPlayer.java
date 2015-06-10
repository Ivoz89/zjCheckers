/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ai;

import com.google.inject.Singleton;
import data.exceptions.InvalidMoveException;
import data.model.Game;
import data.model.Move;
import data.model.Player;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple AI
 * @author Ivo
 */
@Singleton
public class AIPlayer extends Player {

    public AIPlayer(String name, boolean first) {
        super(name, first);
    }

    private AIPlayer() {
    }

    @Override
    public void makeMove(Game game) {
        List<Move> legalMoves = game.getLegalMovesAsStream(this)
                .collect(Collectors.toList());
        Collections.shuffle(legalMoves);
        for (Move move : legalMoves) {
            try {
                game.makeMove(move);
                break;
            } catch (InvalidMoveException ex) {
                //TODO handling
            }
        }
    }

    @Override
    public void makeMove(Game game, List<Move> possibleBeatings) {
        Collections.shuffle(possibleBeatings);
        for (Move move : possibleBeatings) {
            try {
                game.makeMove(move);
                break;
            } catch (InvalidMoveException ex) {
                //TODO handling
            }
        }
    }
}
