package com.legacy.sifarish.POJO;

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
        Checkins checkins = response.checkins;
        for(checkinItems item : checkins.items){
            for(checkinCategory cat : item.venue.categories){
                checkintypes += cat.name + ",";
            }
        }
        if (checkins.count>0) {
            return checkintypes.substring(0, checkintypes.length() - 1);
        }
        else
            return "";
    }
}
