package com.example.dell.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> notes = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnotes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.ai){
            Intent intent = new Intent(getApplicationContext(), noteeditoractivity.class);
            startActivity(intent);
            return true;

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.noteitem);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.dell.notesapp", Context.MODE_PRIVATE);
        HashSet<String > set = new HashSet();
        set = (HashSet<String>) sharedPreferences.getStringSet("tasks", null);
        if (set == null){


            notes.add("Learn Android App development");
            notes.add("Learn react js");
            notes.add("Learn Competetive programming");
        }else {
           notes = new ArrayList(set);
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), noteeditoractivity.class);
                intent.putExtra("noteid", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                  final int itemtodelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure")
                        .setMessage("Delete this task")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemtodelete);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.dell.notesapp", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("tasks", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


    }
}
