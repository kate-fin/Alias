package com.example.alias;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public ArrayList<Word> getWord(String response) throws JSONException {
        ArrayList<Word> dict = new ArrayList<>();
        JSONArray jsonDict = new JSONArray(response);
        for (int i=0; i<jsonDict.length(); i++) {
            JSONObject wordJson = jsonDict.getJSONObject(i);
            String eng_word = wordJson.getString("eng");
            String trans = wordJson.getString("trans");
            String rus_word = wordJson.getString("rus");
            dict.add(new Word(eng_word, trans, rus_word));
        }        return dict;
    }

    private static String convertStreamToString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }
    public static ArrayList<Word> importFromJSON(MainActivity context) throws IOException, JSONException {

        InputStream fileInputStream;
        fileInputStream = context.getResources().openRawResource(R.raw.dict);
        String response = convertStreamToString(fileInputStream);
        JsonParser jsonParser = new JsonParser();
        ArrayList<Word> w = jsonParser.getWord(response);
        return w;
    }
}
