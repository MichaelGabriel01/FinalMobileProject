package com.example.movieplayer.Db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResult;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_POSTER = "poster";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_BACKDROP = "backdrop";
    private static final String COLUMN_RATING = "rating";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_TYPE + " TEXT," +
                COLUMN_POSTER + " TEXT," +
                COLUMN_YEAR + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_BACKDROP + " TEXT," +
                COLUMN_RATING + " REAL" +
                ")";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public boolean isFavorite(String title, String type) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_TYPE + " = ?";
        String[] selectionArgs = { title, type };

        Cursor cursor = null;
        boolean isFavorite = false;

        try {
            cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.getCount() > 0) {
                isFavorite = true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isFavorite;
    }

    public void addFavorite(String title, String type, String poster, String year, String description, String backdrop, double rating) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_POSTER, poster);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_BACKDROP, backdrop);
        values.put(COLUMN_RATING, rating);
        db.insert(TABLE_FAVORITES, null, values);
    }

    public void removeFavorite(String title, String type) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_TITLE + " = ? AND " + COLUMN_TYPE + " = ?",
                new String[]{title, type});
    }
    @SuppressLint("Range")
    public List<MovieResult> getFavoriteMovies() {
        List<MovieResult> favoriteMovies = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES +
                " WHERE " + COLUMN_TYPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"movie"});
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String poster = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER));
                String year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String backdrop = cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP));
                Double rating = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING));

                MovieResult movie = new MovieResult();
                movie.setTitle(title);
                movie.setPosterPath(poster);
                movie.setReleaseDate(year);
                movie.setOverview(description);
                movie.setBackdropPath(backdrop);
                movie.setVoteAverage((double) rating);

                favoriteMovies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteMovies;
    }

    @SuppressLint("Range")
    public List<TvResult> getFavoriteTVShows() {
        List<TvResult> favoriteTVShows = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES +
                " WHERE " + COLUMN_TYPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"tv_show"});
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String poster = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER));
                String year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String backdrop = cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP));
                Double rating = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING));

                TvResult tvShow = new TvResult();
                tvShow.setName(title);
                tvShow.setPosterPath(poster);
                tvShow.setFirstAirDate(year);
                tvShow.setOverview(description);
                tvShow.setBackdropPath(backdrop);
                tvShow.setVoteAverage(rating);

                favoriteTVShows.add(tvShow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteTVShows;
    }
}
