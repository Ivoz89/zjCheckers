package data.model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.annotations.ColumnCount;
import data.annotations.RowCount;
import data.api.BoardPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Stream;
import lombok.Data;

/**
 * Represents the checker board.
 * Created by Ivo on 06/05/15.
 */
@Singleton
@Data 
public class Board {

    public final static int MIN_COLUMN_COUNT = 5;
    public final static int CLASSIC_COLUMN_COUNT = 8;
    public final static int MAX_COLUMN_COUNT = 20;
    public final static int MIN_ROW_COUNT = 5;
    public final static int CLASSIC_ROW_COUNT = 8;
    public final static int MAX_ROW_COUNT = 20;
    private int rowCount;
    private int columnCount;
    private ArrayList<ArrayList<Field>> fields;
    
    private Board() {
    }

    /**
     *
     * @param rowCount
     * @param columnCount
     * @param populator filling the board with checkers
     */
    @Inject
    public Board(@RowCount int rowCount, @ColumnCount int columnCount, @NotNull BoardPopulator populator) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        if (isOfIllegalSize(rowCount, columnCount)) {
            throw new IllegalArgumentException();
        }
        fields = new ArrayList();
        for (int rowNo = 0; rowNo < rowCount; rowNo++) {
            ArrayList<Field> row = new ArrayList();
            for (int columnNo = 0; columnNo < columnCount; columnNo++) {
                Field field = new Field(rowNo, columnNo);
                row.add(field);
            }
            fields.add(row);
        }
        populator.populateBoard(fields);
    }

    /**
     * Checks if given row and column count are within set parameters.
     * @param rowCount
     * @param columnCount
     * @return
     */
    private boolean isOfIllegalSize(int rowCount, int columnCount) {
        return rowCount < MIN_ROW_COUNT || columnCount < MIN_COLUMN_COUNT 
                || rowCount > MAX_ROW_COUNT || columnCount > MAX_COLUMN_COUNT;
    }

    public Stream<Field> getFieldsAsStream() {
        return getFields().stream().flatMap(l -> l.stream());
    }
    
    public Field getField(int row, int column) {
        return fields.get(row).get(column);
    }
}
