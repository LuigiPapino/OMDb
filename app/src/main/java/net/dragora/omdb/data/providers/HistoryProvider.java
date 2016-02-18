package net.dragora.omdb.data.providers;

import android.net.Uri;
import android.support.annotation.NonNull;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by nietzsche on 18/02/16.
 */
@ContentProvider(authority = HistoryProvider.AUTHORITY, database = HistoryDatabase.class)

public final class HistoryProvider {
    public static final String AUTHORITY = "net.dragora.omdb.HistoryProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(@NonNull String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = HistoryDatabase.HISTORY) public static class History {

        @ContentUri(
                path = HistoryDatabase.HISTORY,
                type = "vnd.android.cursor.dir/list",
                defaultSort = HistoryColumns._ID + " DESC")
        public static final Uri HISTORY = Uri.parse("content://" + AUTHORITY + "/" + HistoryDatabase.HISTORY);


    }
}
