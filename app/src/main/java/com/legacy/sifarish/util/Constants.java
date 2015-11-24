package com.legacy.sifarish.util;

import com.legacy.sifarish.POJO.RecommendationItem;

public class Constants {
    public static String CLIENT_ID = "JM5PAT5BDB21OVLDNEJ4GGOJHQVWBM2FCGALM0CQTIW55221";
    public static String CLIENT_SECRET = "ZGFYAZ3AEPEFNBDIDYYN1UUSAS0ZYE2UJZ4HOCIE1LL2YBIR";
    public final static int REQUEST_CODE_FSQ_CONNECT = 200;
    public final static int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;
    public final static String PREF_CONST = "user_details";

    public final static String AWS_URL = "http://ec2-54-153-29-131.us-west-1.compute.amazonaws.com:8080";
    public static RecommendationItem ri = null;

    public static String stores[] = {
            "bestbuy, San Jose Market Center",
            "walmart, San Jose Market Center",
            "kohls, San Jose Market Center",
            "target, San Jose Market Center",
            "target, 272 E Santa Clara St",
            "costco, 272 E Santa Clara St",
            "bestbuy, 272 E Santa Clara St"
    };
}
