package net.dragora.omdb.data;

import android.support.annotation.NonNull;

import net.dragora.omdb.models.ResponseSearch;
import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import java.util.List;
import java.util.Map;

import rx.Observable;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by nietzsche on 18/02/16.
 */
public interface HistoryStore {

    @NonNull
    Observable<Map<String, ResponseSearch>> getHistory();

    void put(@NonNull String keyword, @NonNull ResponseSearch search);


}
