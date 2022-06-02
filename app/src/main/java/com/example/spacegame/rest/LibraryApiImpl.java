package com.example.spacegame.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spacegame.RecordActivity;
import com.example.spacegame.SettinsActivity;
import com.example.spacegame.domain.Record;
import com.example.spacegame.fakedb.LibraryFakeDb;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LibraryApiImpl implements LibraryApi {

    public SharedPreferences mSettings;

    public static String BASE_URL = "http://192.168.0.207:8080";
    private final Context context;


    private Response.ErrorListener errorListener;

    public LibraryApiImpl(Context context) {

        this.context = context;
        BASE_URL = SettinsActivity.BASE_URL1;
        errorListener = new ErrorListenerImpl();
    }


    @Override
    public void fillRecord() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/record";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        LibraryFakeDb.RECORD_LIST.clear();


                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Record record = new com.example.spacegame.domain.mapper.RecordMapper().recordFromJsonArray(jsonObject);

                                LibraryFakeDb.RECORD_LIST.add(record);
                            }
                            Log.d("RECORD_LIST", String.valueOf(response.length()));
                            Log.d("RECORD_LIST", String.valueOf(response.getString(0)));
                            Log.d("RECORD_LIST", LibraryFakeDb.RECORD_LIST.toString());
                            ((RecordActivity) context).update();
                        } catch (JSONException e) {

                            Log.d("RECORD_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }



    @Override
    public void newRecord(Record record) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/record";
        Log.d("Response", "new");

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        // выгрузка заново плохо?
                        fillRecord();
                    }
                },

                errorListener
        ) {
            /*@Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }
            */
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("nameName", record.getName());
                params.put("nameScore", String.valueOf(record.getScore()));

                return params;
            }
        };

        queue.add(postRequest);
    }


    private class ErrorListenerImpl implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {
//            Log.d("Error", error.getMessage());
            Toast.makeText(context, "не удалось подключиться к серверу", Toast.LENGTH_SHORT).show();
            Log.d("Error", BASE_URL);
        }
    }

}
