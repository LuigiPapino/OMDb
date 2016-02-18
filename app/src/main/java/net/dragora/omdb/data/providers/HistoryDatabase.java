package net.dragora.omdb.data.providers;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by nietzsche on 18/02/16.
 */
@Database(version =  HistoryDatabase.VERSION)
public class HistoryDatabase {
    public static final int VERSION =1;
    @Table(HistoryColumns.class) public static final String HISTORY = "HISTORY";

}
