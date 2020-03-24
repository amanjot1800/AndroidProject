package com.example.androidgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class ImageOfTheDay extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    final Calendar pickedCalendar = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    public String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        //source: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        EditText edittext = (EditText) findViewById(R.id.edittext);
        DatePickerDialog.OnDateSetListener dateListener = (view, year, monthOfYear, dayOfMonth) -> {

            pickedCalendar.clear();
            pickedCalendar.set(Calendar.YEAR, year);
            pickedCalendar.set(Calendar.MONTH, monthOfYear);
            pickedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            date = year + "-" + ++monthOfYear + "-" + dayOfMonth;
            edittext.setText(date);
        };

        edittext.setOnClickListener(view -> {

            new DatePickerDialog(ImageOfTheDay.this, dateListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        Button go = findViewById(R.id.go);
        go.setOnClickListener(clk -> {

            if (date ==null || date.isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_LONG).show();
            }
            else if (pickedCalendar.after(myCalendar)){
               Snackbar.make(go, "Cannot Select a future date", Snackbar.LENGTH_LONG).show();
            }
            else {

                Intent goo = new Intent(ImageOfTheDay.this, ImageOfDayLoading.class);
                goo.putExtra("date", date);
                startActivity(goo);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_of_the_day_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item1:
                Intent go = new Intent(this, Class.class);
                startActivity(go);

            case R.id.item2:
                Intent go2 = new Intent(this, Class.class);
                startActivity(go2);

            case R.id.item3:
                Intent go3 = new Intent(this, Class.class);
                startActivity(go3);

            case R.id.item4:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.help)
                        .setMessage("This page will help you to see the Image of day posted by NASA. You can select any date" +
                                " from the date picker and see get the url of thr image.")
                        .setPositiveButton("Ok", (c,clk) -> {

                        }).create().show();
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.item1:
                Intent go = new Intent(this, Class.class);
                startActivity(go);

            case R.id.item2:
                Intent go2 = new Intent(this, Class.class);
                startActivity(go2);

            case R.id.item3:
                Intent go3 = new Intent(this, Class.class);
                startActivity(go3);

            case R.id.item4:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.help)
                        .setMessage("This page will help you to see the Image of day posted by NASA. You can select any date" +
                                " from the date picker and see get the url of thr image.")
                        .setPositiveButton("Ok", (c,clk) -> {

                        }).create().show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}

