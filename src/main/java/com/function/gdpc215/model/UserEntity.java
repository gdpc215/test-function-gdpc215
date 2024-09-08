package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class UserEntity implements Serializable {

    public String id;
    public String strFirstName;
    public String strLastName;
    public String strEmail;
    public String strPassword;
    public String strPasswordSalt;
    public String strProviderName;
    public String strProviderUserId;
    public String strProviderPhotoUrl;
    public LocalDateTime dateBirth;
    public String strGender;
    public String strPhone;
    public String strLanguagePreferences;
    public boolean flgAllowNotifications;
    public boolean flgActiveAccount;
    public boolean flgGhostUser;
    public boolean flgAdmin;
    public LocalDateTime dateLastLogin;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public UserEntity() {
        this.id = "";
        this.strFirstName = "";
        this.strLastName = "";
        this.strEmail = "";
        this.strProviderName = "";
        this.strProviderUserId = "";
        this.strProviderPhotoUrl = "";
        this.dateBirth = LocalDateTime.MIN;
        this.strGender = "";
        this.strPhone = "";
        this.strLanguagePreferences = "";
        this.flgAllowNotifications = false;
        this.flgActiveAccount = false;
        this.flgGhostUser = false;
        this.flgAdmin = false;
        this.dateLastLogin = LocalDateTime.MIN;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public UserEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.strFirstName = jsonObject.optString("strFirstName");
        this.strLastName = jsonObject.optString("strLastName");
        this.strEmail = jsonObject.optString("strEmail");
        this.strPassword = jsonObject.optString("strPassword");
        this.strPasswordSalt = jsonObject.optString("strPasswordSalt");
        this.strProviderName = jsonObject.optString("strProviderName");
        this.strProviderUserId = jsonObject.optString("strProviderUserId");
        this.strProviderPhotoUrl = jsonObject.optString("strProviderPhotoUrl");
        this.dateBirth = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateBirth"));
        this.strGender = jsonObject.optString("strGender");
        this.strPhone = jsonObject.optString("strPhone");
        this.strLanguagePreferences = jsonObject.optString("strLanguagePreferences");
        this.flgAllowNotifications = jsonObject.optBoolean("flgAllowNotifications");
        this.flgActiveAccount = jsonObject.optBoolean("flgActiveAccount");
        this.flgGhostUser = jsonObject.optBoolean("flgGhostUser");
        this.flgAdmin = jsonObject.optBoolean("flgAdmin");
        this.dateLastLogin = jsonObject.isNull("dateLastLogin") ? null
                : JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateLastLogin"));
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
    }

    public static List<UserEntity> getCollectionFromJsonArray(JSONArray array) {
        List<UserEntity> entities = new ArrayList<>();
        if (array.length() > 0) {
            array.forEach((Object obj) -> {
                entities.add(new UserEntity((JSONObject) obj));
            });
        }
        return entities;
    }

    public static UserEntity getSingleFromJsonArray(JSONArray array) {
        UserEntity entity = null;
        List<UserEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
