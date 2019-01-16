package com.ruby.myrecipebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListRecipe extends AppCompatActivity {

    DatabaseHelper myDB;
    Spinner ShortBy;
    Spinner spinnerStnFrom;
    ListView recipeList;
    Button btn_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        myDB = new DatabaseHelper(this);
        recipeList = (ListView) findViewById(R.id.list_recipe);
        btn_search = (Button) findViewById(R.id.btn_search);

        addItemsOnSpinner2();
        loadStationFrom("0","0","By Title");


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // common.showMessage(CheckVehicle.this,"Please Wait.","Downloading data ....");

                EditText regno = (EditText)findViewById(R.id.txt_vr_no);
                String rNo = regno.getText().toString();

                if(rNo.length() > 2  ) {
                    loadStationFrom("0",rNo,"By Title");
                }else{
                    setMessage("Please Insert minimum 2 character");

                }
            }
        });


        recipeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TextView textView = (TextView) view.findViewById(R.id.nameHolder);
                String title = adapterView.getItemAtPosition(i).toString();

                if(title.length() > 2) {
                   // setMessage(title);

                    Intent ii = new Intent(ListRecipe.this, UpdateRecipe.class);
                    ii.putExtra("title", title);
                    startActivity(ii);

                }else{
                   setMessage("INVALID REQUEST");
                }
                return true;
            }});

        ShortBy = (Spinner) findViewById(R.id.txt_sorting);
        ShortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                String shortBy = ShortBy.getSelectedItem().toString();

              //  setMessage(shortBy+String.valueOf(position));
           // setMessage(String.valueOf(position));

                loadStationFrom("0","0",shortBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


       // String shortBy = ShortBy.getSelectedItem().toString();


    }


    public void addItemsOnSpinner2() {

       Spinner sorting = (Spinner) findViewById(R.id.txt_sorting);
        List<String> list = new ArrayList<String>();
        list.add("By Title");
        list.add("Top Rated");
        list.add("Down Rated");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorting.setAdapter(dataAdapter);
    }


    public void loadStationFrom(String ID, String TITLE, String shortBy) {
        //setMessage(TITLE);



        recipeList.setAdapter(null);
        // Spinner Drop down elements
        List<String> lables = myDB.getRecipeInfo(ID,TITLE,shortBy);

        int count_row = lables.size();

        if(count_row >0) {
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
            //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            recipeList.setAdapter(dataAdapter);

        }else{
            setMessage("SORRY, NO RECIPE FOUND !");
        }
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
        Toast.makeText(ListRecipe.this,msg,Toast.LENGTH_LONG).show();
    }
}
