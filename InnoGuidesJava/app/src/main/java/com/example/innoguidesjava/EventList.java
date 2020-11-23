package com.example.innoguidesjava;

/**
 * Class for list of events
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        Intent intent = getIntent();
        // Get list with events
        ArrayList<Event> events = (ArrayList<Event>) intent.getSerializableExtra("Events");

        HashMap<String, String> allEvents = new HashMap<>();

        // Set all events
        for (int i = 0; i < events.size(); i++){
            allEvents.put(events.get(i).getName(), events.get(i).getDate() + " " + events.get(i).getTime());
        }

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = allEvents.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventList.this, MainMap.class);

                intent.putExtra("Event", events.get(Math.toIntExact(id-1)));
            }
        });

        resultsListView.setAdapter(adapter);
    }

}