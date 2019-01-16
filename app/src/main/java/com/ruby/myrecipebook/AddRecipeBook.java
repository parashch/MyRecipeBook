package com.ruby.myrecipebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipeBook extends AppCompatActivity {

    DatabaseHelper myDB;
    Button save_recipe_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_book);



        myDB = new DatabaseHelper(this);
        save_recipe_book = (Button) findViewById(R.id.btn_save_recipe_book);


       // myDB.onCreate(null);

        save_recipe_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = null;
                String instructions = null;
                String param1 = null;
                String param2 = null;
                String param3 = null;

                EditText Title = (EditText)findViewById(R.id.txt_title);
                title = Title.getText().toString();

                EditText Instructions = (EditText)findViewById(R.id.txt_instructions);
                instructions = Instructions.getText().toString();

               // setMessage(title+"---"+instructions);
                String msg;
                if(title.length() > 2) {
                     msg = saveDataToLocal(title, instructions, param1, param2, param3);
                }else{
                     msg = "Please Insert greater than 2 character in title !";
                }

                setMessage(msg);
                Title.getText().clear();
                Instructions.getText().clear();

            }
        });
    }

    public  String saveDataToLocal(String title, String instructions,String param1,String param2,String param3){


        //---------------------- save data to DB
        Boolean save_data = myDB.insertData(title, instructions,param1,param2,param3);

        if(save_data == true){
            return "Recipe saved successfully";
        }else{
            return "Data Not Inserted.";
        }
        //---------------------- save data to DB
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_recipe) {
            //  setMessage("this is me");
            Intent i = new Intent(this, AddRecipeBook.class);
            startActivity(i);
        }else if (id == R.id.action_list_recipe){
            Intent i = new Intent(this, ListRecipe.class);
            startActivity(i);
        }else if (id == R.id.action_home){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }



        return super.onOptionsItemSelected(item);
    }



    public void setMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
