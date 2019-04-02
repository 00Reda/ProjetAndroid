package com.androidprojet.projetandroid.Rest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerMessagesParser {

    private static String getServerMSG(String type,String json) throws Exception{


            JSONObject reader = new JSONObject(json);
            JSONArray array=reader.getJSONArray(type);
            String html="<html><body><center><ul color='red'>";
            for(int i=0;i<array.length();i++){
                html+="<li> <b>"+array.getString(i)+" </b></li>";
            }
            html+="</ul></center></body></html>";
            return html;


    }

    public static String getErrorMassage(String json)throws Exception{
         return ServerMessagesParser.getServerMSG("error",json);
    }

    public static String getDoneMassage(String json)throws Exception{
        return ServerMessagesParser.getServerMSG("msg",json);
    }


}
