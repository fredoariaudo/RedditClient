package com.aariaudo.android.redditclient.data;

import com.aariaudo.android.redditclient.model.RedditEntry;
import com.aariaudo.android.redditclient.views.EntryListView;

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
    public static final String URL_REDDIT_TOP = "https://www.reddit.com/top.json";
    public static final String ENCODING_SERVER = "UTF-8";

    private static HttpDataProvider httpDataProvider;

    public static synchronized HttpDataProvider getInstance()
    {
        if(httpDataProvider == null)
            httpDataProvider = new HttpDataProvider();

        return httpDataProvider;
    }

    private String sendHttpRequest(String urlString, String params)
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

                //Write parameters
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

    public ArrayList<RedditEntry> getEntries(String lastEntryName)
    {
        ArrayList<RedditEntry> entries = new ArrayList<RedditEntry>();

        String response = sendHttpRequest(URL_REDDIT_TOP + "?count=" + EntryListView.NUM_ITEMS_TO_LOAD + (lastEntryName!=null?"&after="+lastEntryName:""), null);

        try
        {
            JSONArray entriesArray = new JSONObject(response).getJSONObject("data").getJSONArray("children");

            for (int i = 0; i < entriesArray.length(); i++)
            {
                JSONObject entryJson = entriesArray.getJSONObject(i).getJSONObject("data");
                RedditEntry entry = new RedditEntry();
                entry.setName(entryJson.getString("name"));
                entry.setTitle(entryJson.getString("title"));
                entry.setAuthor(entryJson.getString("author"));
                entry.setDate(entryJson.getLong("created_utc"));
                entry.setComments(entryJson.getInt("num_comments"));
                entry.setThumbnail(entryJson.getString("thumbnail"));
                entry.setUrl(entryJson.getString("url"));
                entries.add(entry);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return entries;
    }
}
