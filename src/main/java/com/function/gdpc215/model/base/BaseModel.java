package com.function.gdpc215.model.base;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BaseModel {

    public BaseModel() {}

    public BaseModel(JSONObject jsonObject) { }

    public static BaseModel getFromJsonObject(JSONObject jsonObject) {
        return new BaseModel(jsonObject);
    }

    public static List<BaseModel> getCollectionFromJsonArray(JSONArray array) {
        List<BaseModel> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BaseModel((JSONObject) obj));
        });
        return entities;
    }

    public static BaseModel getSingleFromJsonArray(JSONArray array) {
        BaseModel entity = null;
        List<BaseModel> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}