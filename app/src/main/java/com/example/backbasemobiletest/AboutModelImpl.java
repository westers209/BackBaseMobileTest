package com.example.backbasemobiletest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

public class AboutModelImpl implements About.Model {

    private static final String TAG = AboutModelImpl.class.getSimpleName();
    private final About.Presenter presenter;
    private final WeakReference<Context> context;
    private static final int FILE_NAME = R.raw.cities;

    public AboutModelImpl(@NonNull About.Presenter presenter, @NonNull Context context){
        this.presenter = presenter;
        this.context = new WeakReference<>(context);
    }

    @Override
    public void getAboutInfo() {
        String aboutInfoJson = getAboutInfoFromAssets();

        if(aboutInfoJson != null && !aboutInfoJson.isEmpty()){
    		List<AboutInfo> aboutInfo = parseAboutInfo(aboutInfoJson);
    		if (aboutInfo != null){
        		presenter.onSuccess(aboutInfo);
        		return;
   		 	}
		}

		presenter.onFail();
    }

    private List<AboutInfo> parseAboutInfo(String aboutInfoJson) {
        AboutInfo aboutInfo = null;
        List<AboutInfo> aboutInfos = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(aboutInfoJson);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                aboutInfo = new AboutInfo();
                Coordinates coordinates = new Coordinates();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                aboutInfo.setCountry(jsonObject.getString("country"));
                aboutInfo.setName(jsonObject.getString("name"));
                aboutInfo.set_id(jsonObject.getInt("_id"));
                JSONObject coordObject = new JSONObject(jsonObject.getJSONObject("coord").toString());
                coordinates.setLon(coordObject.getDouble("lon"));
                coordinates.setLat(coordObject.getDouble("lat"));
                aboutInfo.setCoord(coordinates);
                aboutInfos.add(aboutInfo);
            }

        } catch (JSONException e) {
            Log.d(TAG, "parseAboutInfo: "+e.getMessage());
        }
        return aboutInfos;
    }

    private String getAboutInfoFromAssets() {

        if(context.get() != null){
            try{
                InputStream file = context.get().getResources().openRawResource(FILE_NAME);
                byte[] formArray = new byte[file.available()];
                file.read(formArray);
                file.close();
                return new String(formArray);
            }catch (IOException ex){
                Log.e(TAG, ex.getLocalizedMessage(), ex);
            }
        }

        return null;
    }
}
