package data.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import controller.ai.AIPlayer;
import controller.logic.processing.RuleProcessor;
import data.annotations.HumanPlayer;
import data.annotations.CPUPlayer;
import data.events.AnotherBeatingPossibleEvent;
import data.events.MoveMadeEvent;
import data.events.NextTurnEvent;
import data.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Data;

/**
 * Represents a checker game between two players Created by Ivo on 06/05/15.
 */
@Data
public class Game {

    private Player humanPlayer;
    private AIPlayer aiPlayer;
    private Board board;
    boolean repeatedMove;
    private transient EventBus eventBus;
    private transient RuleProcessor ruleController;

    /**
     * @param player1
     * @param player2
     * @param board
     * @param ruleController chain of responsibility responsible for analysing
     * and executing moves
     * @param eventBus
     */
    @Inject
    public Game(@HumanPlayer Player player1, @CPUPlayer AIPlayer player2, Board board, RuleProcessor ruleController,
            EventBus eventBus) {
        this.humanPlayer = player1;
        this.aiPlayer = player2;
        this.board = board;
        this.ruleController = ruleController;
        this.eventBus = eventBus;
        repeatedMove = false;
        eventBus.register(this);
    }

    private Game() {
    }
    
    /**
     * Passes the Move to the MoveProcessor chain of responsibility
     * @param move
     * @throws InvalidMoveException 
     */
    public void makeMove(Move move) throws InvalidMoveException {
        ruleController.process(move);
    }
    
    /**
     * @param event event
     */
    @Subscribe
    public void handleAnotherBeatingEvent(AnotherBeatingPossibleEvent event) {
        if (event.getPlayer() == humanPlayer) {
            repeatedMove = true;
        } else {
            aiPlayer.makeMove(this, event.getPossibleBeatings());
        }
    }

    /**
     * @param event
     */
    @Subscribe
    public void handleNextTurnEvent(NextTurnEvent event) {
        if (!repeatedMove) {
            aiPlayer.makeMove(this);
        }
        repeatedMove = false;
        eventBus.post(new MoveMadeEvent());
    }

    /**
     * 
     * @param source Field
     * @return List of legal moves for a Checker in source
     */
    public List<Move> getLegalMoves(Field source) {
        List<Move> legalMoves = new ArrayList<>();
        getLegalDestinations(source).stream()
                .forEach(d -> legalMoves.add(new Move(source.getOccupant().getPlayer(), source, d)));
        return legalMoves;
    }

    /**
     * 
     * @param player
     * @return List of all legal Moves for the Player
     */
    public Stream<Move> getLegalMovesAsStream(Player player) {
        return board.getFieldsAsStream().
                filter(f -> f.getOccupant() != null && f.getOccupant().getPlayer() == player).
                map(field -> getLegalMoves(field)).
                flatMap(l -> l.stream());
    }
    
    /**
     * Finds all possible destinations for a checker on the given field
     *
     * @param source
     * @return List of legal destinations
     */
    public List<Field> getLegalDestinations(Field source) {
        List<Field> legalDestinations = new ArrayList<>();
        board.getFieldsAsStream().forEach(destination -> {
            try {
                Move checkedMove = new Move(source.getOccupant().getPlayer(), source, destination);
                ruleController.checkIfLegal(checkedMove);
                legalDestinations.add(checkedMove.getDestinationField());
            } catch (InvalidMoveException ex) {
            }
        });
        return legalDestinations;
    }

}
