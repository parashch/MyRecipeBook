package com.ruby.myrecipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateRecipe extends AppCompatActivity {

    DatabaseHelper myDB;
    String TITLE;
    String _id;

    String title = null;
    String instructions = null;
    String param1 = null;
    String param2 = null;
    String param3 = null;
    String Rating = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        myDB = new DatabaseHelper(this);
        Bundle b = getIntent().getExtras();
        TITLE = b.getString("title");

        showData(TITLE);

        Button updateBtn = (Button)findViewById(R.id.btn_save_recipe_book);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });


        Button deleteBtn = (Button)findViewById(R.id.btn_delete_recipe_book);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    public void updateData(){

        EditText Title = (EditText)findViewById(R.id.txt_title);
        title = Title.getText().toString();

        EditText Instructions = (EditText)findViewById(R.id.txt_instructions);
        instructions = Instructions.getText().toString();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.txt_rating);
        Rating = String.valueOf(ratingBar.getRating());

      Boolean res =  myDB.updateData(_id,title,instructions,Rating);

      if(res){
          setMessage("Recipe Updated.");
      }else{
          setMessage("Error, Cannot Update Recipe !");
      }
    }

    public void deleteData(){

        Integer res =  myDB.deleteData(_id);

        if(res>0){
            setMessage("Recipe Deleted.");
            Intent ii = new Intent(UpdateRecipe.this, ListRecipe.class);
            startActivity(ii);

        }else{
            setMessage("Error, Cannot Delete Recipe !");
        }

    }


    public  void showData(String TITLE){


        String _title = null;
        String _instructions = null;
        String _param1 = null;
        String _param2 = null;
        String _param3 = null;
        String _rating = null;
        _id = null;


            Cursor result = myDB.getRecipeInfoData(TITLE);

        if (result.moveToFirst()) {
            do {

                _title = result.getString(0);
                _instructions = result.getString(1);
                _param1 = result.getString(2);
                _param2 = result.getString(3);
                _param3 = result.getString(4);
                _rating = result.getString(5);
                _id = result.getString(6);




                TextView _lblWebData = (TextView)findViewById(R.id.lblWebData);
                _lblWebData.setText("RECIPE ID IS :"+_id);

                EditText Title = (EditText)findViewById(R.id.txt_title);
                Title.setText(_title);

                EditText Instructions = (EditText)findViewById(R.id.txt_instructions);
                Instructions.setText(_instructions);


                if(_rating == null){
                    _rating = "0";
                }

                RatingBar ratingBar = (RatingBar) findViewById(R.id.txt_rating);
                ratingBar.setRating(Float.parseFloat(_rating));

            } while (result.moveToNext());
        }else{
            setMessage("INVALID REQUEST !");
        }
        result.close();


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
        Toast.makeText(UpdateRecipe.this,msg,Toast.LENGTH_LONG).show();
    }




}
