package controller.logic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.annotations.ColumnCount;
import data.annotations.RowCount;
import data.model.Board;
import data.model.Field;
import data.model.Move;
import data.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for checking various board conditions
 */
@Singleton
public class BoardHelper {

    private final int rowCount;
    private final int columnCount;
    private final Board board;

    @Inject
    public BoardHelper(@RowCount int rowCount, @ColumnCount int columnCount, @NotNull Board board) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.board = board;
    }

    /**
     * Finds all possible beating moves for a player
     * @param player
     * @return List of beating moves
     */
    public List<Move> beatingIsPossible(@NotNull Player player) {
        return board.getFields()
                .stream()
                .flatMap(list -> list.stream())
                .filter(field -> !field.isEmpty() && field.getOccupant().getPlayer() == player)
                .flatMap(field -> getBeatingMovesForField(field).stream())
                .collect(Collectors.toList());
    }

    /**
     * 
     * @param field1
     * @param field2
     * @return Field between Fields in argument
     */
    public Field getFieldBetweenFields(@NotNull Field field1, @NotNull Field field2) {
        int column = (field1.getColumnIndex() + field2.getColumnIndex()) / 2;
        int row = (field1.getRowIndex() + field2.getRowIndex()) / 2;
        return board.getField(row, column);
    }

    /**
     * Finds Field in between source and destination Fields in a Move
     * @param move
     * @return Field 
     */
    public Field getMiddleField(@NotNull Move move) {
        return getFieldBetweenFields(move.getSourceField(), move.getDestinationField());
    }

    /**
     * 
     * @param move to be chacked
     * @return true if argument is a beating Move
     */
    public boolean checkIfBeatingMove(@NotNull Move move) {
        Field source = move.getSourceField();
        Field destination = move.getDestinationField();
        if (Math.abs(source.getColumnIndex() - destination.getColumnIndex()) == 1
                || Math.abs(source.getRowIndex() - destination.getRowIndex()) == 1) {
            return false;
        }
        Field between = getFieldBetweenFields(source, destination);
        return Math.abs(source.getRowIndex() - destination.getRowIndex()) == 2
                && Math.abs(source.getColumnIndex() - destination.getColumnIndex()) == 2
                && destination.getOccupant() == null
                && between.getOccupant() != null
                && between.getOccupant().getPlayer() != move.getPlayer();
    }

    /**
     * @param source
     * @return List of pssible beating moves
     */
    public List<Move> getBeatingMovesForField(@NotNull Field source) {
        List<Move> beatingMoves = new ArrayList<>();
        if (source.isEmpty()) {
            return beatingMoves;
        }
        for (int rowDir = -2; rowDir <= 2; rowDir += 4) {
            for (int colDir = -2; colDir <= 2; colDir += 4) {
                int destRow = source.getRowIndex() + rowDir;
                int destCol = source.getColumnIndex() + colDir;
                if (destRow < rowCount && destRow >= 0 && destCol < columnCount && destCol >= 0) {
                    Field destination = board.getFields().get(destRow).get(destCol);
                    Move move = new Move(source.getOccupant().getPlayer(), source, destination);
                    if (checkIfBeatingMove(move)) {
                        beatingMoves.add(move);
                    }
                }
            }
        }
        return beatingMoves;
    }
}
