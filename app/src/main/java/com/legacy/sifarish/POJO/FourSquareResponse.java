package com.legacy.sifarish.POJO;

import android.util.Log;

import java.util.List;

public class FourSquareResponse {
    public Meta meta;
    public Response response;

    public class Meta{
        public String code;
        public String requestId;
    }

    public class Response{
        public Checkins checkins;
    }

    public class Checkins{
        public int count;
        public List<checkinItems> items;
    }

    public class checkinItems{
        public Venue venue;
    }

    public class Venue{
        List<checkinCategory> categories;
    }

    public class checkinCategory{
        String name;
    }

    @Override
    public String toString(){
        String checkintypes = "";
        Log.d("toString ","before reponse checkins ");
        Checkins checkins = response.checkins;
        Log.d("Checkinss : ",checkins.toString());
        Log.d("toString ","after reponse checkins ");
        for(checkinItems item : checkins.items){
            for(checkinCategory cat : item.venue.categories){
                checkintypes += cat.name + ",";
                Log.d("toString ","inside for for");
            }
            Log.d("toString ","inside for");
        }
        Log.d("toString ","outside for");
        if (checkins.count>0) {
            Log.d("sau", "inside if");
            return checkintypes.substring(0, checkintypes.length() - 1);
        }
        else
            return "";

    }
}
