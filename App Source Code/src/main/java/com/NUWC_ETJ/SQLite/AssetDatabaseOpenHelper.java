package com.NUWC_ETJ.SQLite;

/**
 * Created by Sidhwen on 2/3/2017.
 */
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

public class AssetDatabaseOpenHelper {

    private static final String DB_NAME = "NUWC.db";

    private Context context;

    //gives the Helper the context (current state) of the activity calling it
    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        //if the database doesn't exist, try copying it
        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        //return database
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDatabase(File dbFile) throws IOException {
        //output database

            InputStream is = context.getAssets().open(DB_NAME);


        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}