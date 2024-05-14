package com.function.gdpc215.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class UserEntity {

    public String id;
    public String strFirstName;
    public String strLastName;
    public String strEmail;
    public String strPassword;
    public String strLoginByProvider;
    public Date dateBirth;
    public String strGender;
    public String strPhone;
    public String strLanguagePreferences;
    public boolean flgAllowNotifications;
    public boolean flgActiveAccount;
    public boolean flgGhostUser;
    public Date dateLastLogin;
    public Date dateCreation;
    public Date dateModification;

    public UserEntity() {
        this.id = "";
        this.strFirstName = "";
        this.strLastName = "";
        this.strEmail = "";
        this.strLoginByProvider = "";
        this.dateBirth = new Date(0);
        this.strGender = "";
        this.strPhone = "";
        this.strLanguagePreferences = "";
        this.flgAllowNotifications = false;
        this.flgActiveAccount = false;
        this.flgGhostUser = false;
        this.dateLastLogin = new Date(0);
        this.dateCreation = new Date(0);
        this.dateModification = new Date(0);
    }

    public UserEntity(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.strFirstName = jsonObject.getString("strFirstName");
        this.strLastName = jsonObject.getString("strLastName");
        this.strEmail = jsonObject.getString("strEmail");
        this.strPassword = jsonObject.getString("strPassword");
        this.strLoginByProvider = jsonObject.getString("strLoginByProvider");
        this.dateBirth = JsonUtilities.getDateFromJsonString(jsonObject.getString("dateBirth"));
        this.strGender = jsonObject.getString("strGender");
        this.strPhone = jsonObject.getString("strPhone");
        this.strLanguagePreferences = jsonObject.getString("strLanguagePreferences");
        this.flgAllowNotifications = jsonObject.getBoolean("flgAllowNotifications");
        this.flgActiveAccount = jsonObject.getBoolean("flgActiveAccount");
        this.flgGhostUser = jsonObject.getBoolean("flgGhostUser");
        this.dateLastLogin = jsonObject.isNull("dateLastLogin") ? null : new Date(jsonObject.getLong("dateLastLogin"));
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public UserEntity(String id, String strFirstName, String strLastName, String strEmail, String strPassword,
            String strLoginByProvider, Date dateBirth, String strGender, String strPhone,
            String strLanguagePreferences, boolean flgAllowNotifications, boolean flgActiveAccount,
            boolean flgGhostUser, Date dateLastLogin, Date dateCreation, Date dateModification) {
        this.id = id;
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        this.strEmail = strEmail;
        this.strPassword = strPassword;
        this.strLoginByProvider = strLoginByProvider;
        this.dateBirth = dateBirth;
        this.strGender = strGender;
        this.strPhone = strPhone;
        this.strLanguagePreferences = strLanguagePreferences;
        this.flgAllowNotifications = flgAllowNotifications;
        this.flgActiveAccount = flgActiveAccount;
        this.flgGhostUser = flgGhostUser;
        this.dateLastLogin = dateLastLogin;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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
