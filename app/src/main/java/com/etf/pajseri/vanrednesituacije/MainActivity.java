package com.etf.pajseri.vanrednesituacije;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MainActivity";
    private String feedUrl;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        feedUrl = getString(R.string.host_adress) + "/list";
        System.out.println(feedUrl);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new EventRecycleViewAdapter(new ArrayList<EventObject>());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        new GetEventsTask().execute(feedUrl);

    }


    @Override
    protected void onResume() {
        super.onResume();
        ((EventRecycleViewAdapter) mAdapter).setOnItemClickListener(new EventRecycleViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Intent eventIntent = new Intent(MainActivity.this, EventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", ((EventRecycleViewAdapter)mAdapter).getItem(position).getId());
                eventIntent.putExtras(bundle);
                startActivity(eventIntent);
            }
        });
    }

    @Override
    public void onRefresh() {
        System.out.println("onRefresh is called");

        new GetEventsTask().execute(feedUrl);
    }

    private void updateList(ArrayList<EventObject> results){

        ((EventRecycleViewAdapter)mAdapter).clearData();
        for (int i = 0; i < results.size(); i++){
            ((EventRecycleViewAdapter)mAdapter).addItem(results.get(i), i);
        }
        mAdapter.notifyDataSetChanged();

        System.out.println("updateList is called");

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    private class GetEventsTask extends AsyncTask<String, Void, ArrayList<EventObject>> {

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(ArrayList<EventObject> result) {
            System.out.println("on post execute: ");
            for(EventObject item : result)
                System.out.println(item);

            updateList(result);
        }

        @Override
        protected ArrayList<EventObject> doInBackground(String... params) {
            //android.os.Debug.waitForDebugger();
            System.out.println("doInBackground is called");

            // getting JSON string from URL
            JSONArray json = null;
            try {
                json = new JSONArray(getJSONFromUrl(params[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //parsing json data
            ArrayList<EventObject> results = parseJson(json);
            return results;
        }

    }

    public static ArrayList<EventObject> parseJson(JSONArray json){
        ArrayList<EventObject> results = new ArrayList<>();

        for(int i=0;i<json.length();i++){
            try {
                JSONObject item = json.getJSONObject(i);

                EventObject event = new EventObject();
                event.setTitle(item.getString("title"));
                event.setDescription(item.getString("note"));
                event.setId(item.getString("id"));
                event.setManpower(item.getString("available_manpower"));

                results.add(event);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    /*
    reads the data from the bufferedReader until the the empty line is hit
    return sb: reponse data in String form
     */
    public static String readFromResponse(BufferedReader bufferedReader) throws IOException {
        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /*
    makes a Http Get Request to the uri address
     */
    public static String getJSONFromUrl(String uri){
        String result = null;
        HttpURLConnection connection = null;
        try {
            // establish a connection
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();

            // specify the parameter type
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            // specify the request method
            connection.setRequestMethod("GET");
            connection.connect();

            // read the response data
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            result = readFromResponse(reader);
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if( connection != null)
                connection.disconnect();
        }
        return result;
    }


}
