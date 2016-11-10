package com.aariaudo.android.redditclient.data;

import com.aariaudo.android.redditclient.model.RedditEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpDataProvider
{
    public static final String ENCODING_SERVER = "UTF-8";
    private static HttpDataProvider httpDataProvider;

    public static synchronized HttpDataProvider getInstance()
    {
        if(httpDataProvider == null)
            httpDataProvider = new HttpDataProvider();

        return httpDataProvider;
    }

    public String sendHttpRequest(String urlString, String params)
    {
        StringBuilder response = new StringBuilder();

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(params !=null && !params.isEmpty())
            {
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                //Escribe los parametros
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, ENCODING_SERVER));
                writer.write(params);
                writer.flush();
                writer.close();
                os.close();
            }

            int respCode = connection.getResponseCode();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING_SERVER), 8);

            String line;

            while ((line = reader.readLine()) != null)
            {
                response.append(line);
            }
            inputStream.close();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return response.toString();
    }

    public ArrayList<RedditEntry> getEntries()
    {
        ArrayList<RedditEntry> entries = new ArrayList<RedditEntry>();

        String response = sendHttpRequest("https://www.reddit.com/top.json", null);

        try
        {
            JSONArray entriesArray = new JSONObject(response).getJSONObject("data").getJSONArray("children");

            for (int i = 0; i < entriesArray.length(); i++)
            {
                JSONObject entryJson = entriesArray.getJSONObject(i).getJSONObject("data");
                RedditEntry entry = new RedditEntry();
                entry.setTitle(entryJson.getString("title"));
                entry.setAuthor(entryJson.getString("author"));
                entry.setDate(entryJson.getLong("created"));
                entry.setComments(entryJson.getInt("num_comments"));
                entry.setThumbnail(entryJson.getString("thumbnail"));
                entries.add(entry);
            }
        }
        catch(JSONException e)
        {
        }

        return entries;
    }
}
