package net.dragora.omdb.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;

import net.dragora.omdb.data.providers.HistoryColumns;
import net.dragora.omdb.data.providers.HistoryProvider;
import net.dragora.omdb.models.ResponseSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nietzsche on 18/02/16.
 */
public class HistoryStoreFromProvider implements HistoryStore {
    private static final String TAG = HistoryStoreFromProvider.class.getSimpleName();
    private Context context;
    private BehaviorSubject<Map<String, ResponseSearch>> behaviorSubject = BehaviorSubject.create();


    public HistoryStoreFromProvider(Context context) {
        this.context = context;
        // register for changes
        getContentResolver()
                .registerContentObserver(HistoryProvider.History.HISTORY, true, getContentObserver());
        // initialize the behaviorSubject with the history
        queryAndNext();
    }

    @Override
    @NonNull
    public Observable<Map<String, ResponseSearch>> getHistory() {
        Log.d(TAG, "getHistory() called with: " + "");
        return behaviorSubject.asObservable();

    }

    @Override
    public void put(@NonNull String keyword, @NonNull ResponseSearch search) {
        ContentValues values = new ContentValues();
        try {
            values.put(HistoryColumns._ID, keyword.hashCode());
            values.put(HistoryColumns.KEYWORD, keyword);

            values.put(HistoryColumns.JSON, LoganSquare.serialize(search));
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }

        Cursor cursor = getContentResolver().query(HistoryProvider.History.HISTORY,
                new String[]{HistoryColumns._ID}
                , HistoryColumns._ID + " = ?", new String[]{String.valueOf(keyword.hashCode())}, null);
        if (cursor != null && cursor.getCount() > 0)
            getContentResolver().update(HistoryProvider.History.HISTORY, values, HistoryColumns._ID + " = ?", new String[]{String.valueOf(keyword.hashCode())});
        else
            getContentResolver().insert(HistoryProvider.History.HISTORY, values);
        if (cursor != null)
            cursor.close();
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    @NonNull
    protected ContentObserver getContentObserver() {
        HandlerThread handlerThread = new HandlerThread(this.getClass().getSimpleName());
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        return new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.v(TAG, "onChange(" + uri + ")");
                queryAndNext();
            }
        };
    }

    /**
     * Query the provider and add the result to the Subject
     */
    private void queryAndNext() {
        Cursor cursor = getContentResolver().query(HistoryProvider.History.HISTORY,
                new String[]{HistoryColumns._ID, HistoryColumns.KEYWORD, HistoryColumns.JSON}
                , null, null, null);
        behaviorSubject.onNext(cursorToList(cursor));
    }

    @NonNull
    private Map<String, ResponseSearch> cursorToList(@Nullable Cursor cursor) {
        HashMap<String, ResponseSearch> history = new HashMap<>(0);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    history = new HashMap<>(cursor.getCount());
                    int jsonColIndex = cursor.getColumnIndex(HistoryColumns.JSON);
                    int keywordColIndex = cursor.getColumnIndex(HistoryColumns.KEYWORD);

                    do {
                        try {
                            history.put(cursor.getString(keywordColIndex),
                                    LoganSquare.parse(cursor.getString(jsonColIndex), ResponseSearch.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                            //TODO
                        }
                    } while (cursor.moveToNext());
                }

            } finally {
                cursor.close();
            }
        }

        return history;
    }
}
