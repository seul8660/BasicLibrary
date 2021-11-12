package com.library.base.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.hjq.gson.factory.GsonFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘元强
 * 2021-11-12
 * 15:33
 */
public class GsonUtils {

    private final static Gson GSON = GsonFactory.getSingletonGson();

    public static <T> T parseJsonObject(String jsonText, Class<T> cls){

      return GSON.fromJson(jsonText,cls);
    }

    public static <T> T parseJsonObject(JsonElement jsonText, Class<T> cls){

        return GSON.fromJson(jsonText,cls);
    }


    public static <T> List<T> parseJsonArray(String jsonText, Class<T> cls){

        List<T> list = new ArrayList<>();

        JsonArray elements = JsonParser.parseString(jsonText).getAsJsonArray();

        for(JsonElement  object: elements){

            list.add(parseJsonObject(object,cls));

        }
        return list;
    }


   public static class JsonArrayBuilder {

        private final static JsonArrayBuilder BUILDER = new JsonArrayBuilder();

        private JsonArray jsonArray;

        public static JsonArrayBuilder build(){

            BUILDER.jsonArray = new JsonArray();

            return BUILDER;
        }

        public <T> JsonArrayBuilder add(T value){

            if(value instanceof String) {

                jsonArray.add((String) value);
            }
            else if(value instanceof Number){

                jsonArray.add((Number) value);
            }
            else if(value instanceof Boolean){

                jsonArray.add((Boolean) value);
            }
            else if(value instanceof JsonElement){

                jsonArray.add((JsonElement)value);
            }
            else{
                throw new JsonParseException("unSupport type of put jsonElement");
            }
            return BUILDER;
        }

        public JsonArray create(){

            return jsonArray;
        }

    }

    public static class JsonObjectBuilder {

        private final static JsonObjectBuilder BUILDER = new JsonObjectBuilder();

        private JsonObject jsonObject;

        public static JsonObjectBuilder build(){

            BUILDER.jsonObject = new JsonObject();

            return BUILDER;
        }

        public <T> JsonObjectBuilder put(String key,T value){

            if(value instanceof String) {

                jsonObject.addProperty(key,(String) value);
            }
            else if(value instanceof Number){

                jsonObject.addProperty(key,(Number) value);
            }
            else if(value instanceof Boolean){

                jsonObject.addProperty(key,(Boolean) value);
            }
            else if(value instanceof JsonElement){

                jsonObject.add(key,(JsonElement)value);
            }
            else{
                throw new JsonParseException("unSupport type of put jsonElement");
            }
            return BUILDER;
        }

        public JsonObject create(){

            return jsonObject;
        }

    }


}
