package com.example.innoguidesjava;

/**
 * Page to do search through list of places
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBar extends AppCompatActivity {
    HashMap<String, Place> MapPlaces;
    List<Place> places;
    ArrayAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        try {
            MapPlaces= JSONHelper.MapPlaceJSONFile(this);
            places= JSONHelper.readPlaceJSONFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);

        ArrayList list = new ArrayList();
        for (Place temp:places) list.add(temp.getName());

        // Create list with all places
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        // Go to the map and zoom to chosen place
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SearchBar.this, MainMap.class);
                Place t = MapPlaces.get(listView.getItemAtPosition(position).toString());
                String c1 = String.valueOf(t.getC1());
                String c2 = String.valueOf(t.getC2());
                intent.putExtra("c1",c1);
                intent.putExtra("c2",c2);

                startActivity(intent);
            }
        });

        listView.setEmptyView(findViewById(R.id.noResults));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar,menu);
        MenuItem menuItem=menu.findItem(R.id.search);

        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
