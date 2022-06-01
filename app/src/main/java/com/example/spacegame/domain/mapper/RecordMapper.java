package com.example.spacegame.domain.mapper;

import com.example.spacegame.domain.Record;

import org.json.JSONException;
import org.json.JSONObject;

public class RecordMapper {

    public Record recordFromJsonArray(JSONObject jsonObject) {

        Record record = null;
        try {

            record = new Record(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getInt("score")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return record;
    }

}
