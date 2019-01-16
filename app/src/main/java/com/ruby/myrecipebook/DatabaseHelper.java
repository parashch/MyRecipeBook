package com.ruby.myrecipebook;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by parash on 12:12 12/12/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static  final String DATABASE_NAME = "recipe_book.db";
    public  static  final String TABLE_NAME = "recipe_info";
    public  static  final String col_1 = "id";
    public  static  final String col_2 = "title";
    public  static  final String col_3 = "instructions";
    public  static  final String col_4 = "param1";
    public  static  final String col_5 = "param2";
    public  static  final String col_6 = "param3";
    public  static  final String col_7 = "rating";

    //SQLiteDatabase DB = this.getReadableDatabase();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table "+TABLE_NAME+" ("+col_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+col_2+" TEXT,"+col_3+" TEXT,"+col_4+" TEXT,"+col_5+" TEXT,"+col_6+" TEXT,"+col_7+" TEXT)");

    }


    public boolean alterTable() {
       SQLiteDatabase DB = this.getReadableDatabase();
        DB.execSQL("ALTER TABLE "+TABLE_NAME+" ADD COLUMN sn INTEGER DEFAULT 0");
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(DB);
    }


    public Boolean insertData(String title, String instructions,String param1,String param2,String param3){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValue = new ContentValues();
        contentValue.put(col_2,title);
        contentValue.put(col_3,instructions);
        contentValue.put(col_4,param1);
        contentValue.put(col_5,param2);
        contentValue.put(col_6,param3);


            long result = db.insert(TABLE_NAME, null, contentValue);


       if(result == -1){
           return false;
       }else{
           return true;
       }


    }

    public Cursor getAlldata(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME,null);
      //  Cursor result = db.rawQuery("select station_from, station_to from "+TABLE_NAME,null);
        return result;
    }

    public boolean  updateData(String id, String title,String instructions,String Rating){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValue = new ContentValues();

        contentValue.put(col_2,title);
        contentValue.put(col_3,instructions);
        contentValue.put(col_7,Rating);

        try {
            db.update(TABLE_NAME, contentValue, "id=?", new String[]{id});

            return true;
        }catch (Exception ee){
            return false;
        }

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValue = new ContentValues();
        Integer result =  db.delete(TABLE_NAME,"id=?",new String[]{id});
      //  Integer result =  db.delete(TABLE_NAME,null, null);
        db.execSQL("VACUUM");
      return result;

    }




    public List<String> getRecipeInfo(String ID, String TITLE,String shortBy){
        List<String> labels = new ArrayList<String>();

        String Short;

        if(shortBy == "By Title"){
                Short = "1";
        }else if(shortBy == "Top Rated"){
            Short = "6 desc";
        }else if(shortBy == "Down Rated"){
            Short = "6 ";
        }else{
            Short = "1";
        }

        // Select All Query
        String selectQuery = "SELECT upper(title),instructions,param1,param2,param3,rating,id FROM "+ TABLE_NAME+" i where (upper(i.title) like upper('%"+TITLE+"%') or upper('"+TITLE+"') = '0') ORDER BY "+Short;
        // String selectQuery = "SELECT * FROM " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }




    public Cursor getRecipeInfoData(String TITLE){
        List<String> labels = new ArrayList<String>();

        String selectQuery ="SELECT upper(title),instructions,param1,param2,param3,rating,id FROM "+ TABLE_NAME+" i where upper(i.title) = upper('"+TITLE+"')";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                labels.add("\n  CLASS: "+cursor.getString(0)+" ("+cursor.getString(1)+".00 TAKA)\n");
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
       // db.close();

        return  cursor;
       // return labels.toString().replace("[", "").replace("]", "").replace(",", "")+"\n ";
    }








}


