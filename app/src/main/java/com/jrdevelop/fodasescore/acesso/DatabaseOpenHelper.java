package com.jrdevelop.fodasescore.acesso;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jrdevelop.fodasescore.util.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by renan.b on 27/09/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static DatabaseOpenHelper mInstance = null;

    final private static Integer VERSION = 1;

    private SQLiteDatabase mDataBase;

    private static Context mContext = null;


    public static DatabaseOpenHelper getInstance(Context ctx){
        if (mInstance == null){
            mInstance = new DatabaseOpenHelper(ctx.getApplicationContext());
            mContext = ctx;
        }
        return mInstance;
    }

    private DatabaseOpenHelper(Context ctx){
        super(ctx, Config.DB_NAME, null, VERSION);
    }


    /*public DatabaseOpenHelper(Context context) {
        super(context, Config.DB_NAME, null, VERSION);
        this.mContext = context;
    }*/


    public SQLiteDatabase getConnection(){
        if(mDataBase == null)
            openDataBase();

        if(!mDataBase.isOpen())
            openDataBase();

        return mDataBase;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){

        }
        //do nothing - database already exist
        else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = Config.DB_PATH + Config.DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(Config.DB_NAME);

        // Path to the just created empty db
        String outFileName = Config.DB_PATH + Config.DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = Config.DB_PATH + Config.DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {

        if(mDataBase != null)
            mDataBase.close();

        mDataBase = null;
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
