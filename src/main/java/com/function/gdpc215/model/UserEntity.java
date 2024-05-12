package com.function.gdpc215.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;
import com.function.gdpc215.utils.Utils;

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
        id = "";
        strFirstName = "";
        strLastName = "";
        strEmail = "";
        strPassword = "";
        strLoginByProvider = "";
        dateBirth = new Date(0);
        strGender = "";
        strPhone = "";
        strLanguagePreferences = "";
        flgAllowNotifications = false;
        flgActiveAccount = false;
        flgGhostUser = false;
        dateLastLogin = new Date(0);
        dateCreation = new Date(0);
        dateModification = new Date(0);
    }

    public UserEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);
        try {
            id = jsonObject.getString("id");
            strFirstName = jsonObject.getString("strFirstName");
            strLastName = jsonObject.getString("strLastName");
            strEmail = jsonObject.getString("strEmail");
            strPassword = jsonObject.getString("strPassword");
            strLoginByProvider = jsonObject.getString("strLoginByProvider");
            dateBirth = sdfDate.parse(jsonObject.getString("dateBirth"));
            strGender = jsonObject.getString("strGender");
            strPhone = jsonObject.getString("strPhone");
            strLanguagePreferences = jsonObject.getString("strLanguagePreferences");
            flgAllowNotifications = jsonObject.getBoolean("flgAllowNotifications");
            flgActiveAccount = jsonObject.getBoolean("flgActiveAccount");
            flgGhostUser = jsonObject.getBoolean("flgGhostUser");
            dateLastLogin = jsonObject.isNull("dateLastLogin") ? null : new Date(jsonObject.getLong("dateLastLogin"));
            dateCreation = sdfDate.parse(jsonObject.getString("dateCreation"));
            dateModification = sdfDate.parse(jsonObject.getString("dateModification"));
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
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
        List<UserEntity> entities = new ArrayList<UserEntity>();
        if (array.length() > 0) {
            array.forEach(
                new Consumer<Object>() { 
                    @Override
                    public void accept(Object obj){ entities.add(new UserEntity((JSONObject) obj)); } 
                }
            );
        }
        return entities;
    }
    
    public static UserEntity getSingleFromJsonArray(JSONArray array) {
        UserEntity entity = null;
        List<UserEntity> entities = getCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
}
