package com.function.gdpc215.logic;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.UserDB;
import com.function.gdpc215.model.UserEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class User {

    public static Object hubUser(String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        return switch (subRoute) {
            case "get" ->
                fnUser_GetById(request, connectionString);
            case "create-ghost" ->
                fnUser_CreateGhost(connectionString);
            case "login-with-email" ->
                fnUser_LoginWithEmail(request, connectionString);
            case "login-with-socials" ->
                fnUser_LoginWithSocials(request, connectionString);
            case "update" ->
                fnUser_Update(request, connectionString);
            case "deactivate" ->
                fnUser_Deactivate(request, connectionString);
            default ->
                request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnUser_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String userId = request.getQueryParameters().get("id");

        if (!Utils.validateUUID(userId)) {
            userId = "";
        }
        if (userId.equals("")) {
            return fnUser_CreateGhost(connectionString);
        } else {
            UserEntity entity = UserDB.fnUser_GetById(connectionString, userId);
            // Returns a ghost user if the id provided doesnt return a valid user
            if (entity != null) {
                entity.strPasswordSalt = null;
                return entity;
            } else {
                return fnUser_CreateGhost(connectionString);
            }
        }
    }

    private static Object fnUser_CreateGhost(String connectionString)
            throws Exception {
        UserEntity entity = UserDB.fnUser_CreateGhost(connectionString);
        return entity;
    }

    private static Object fnUser_LoginWithEmail(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            UserEntity entity = new UserEntity(jsonBody);

            // Cast result to appropiate type
            UserEntity returnEntity = UserDB.fnUser_LoginWithSocials(connectionString, entity);

            return returnEntity;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_LoginWithSocials(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            UserEntity entity = new UserEntity(jsonBody);

            // Cast result to appropiate type
            UserEntity returnEntity = UserDB.fnUser_LoginWithSocials(connectionString, entity);

            return returnEntity;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            JSONObject jsonBody = new JSONObject(body.get());
            UserEntity entity = new UserEntity(jsonBody);
            UserDB.fnUser_UpdateInfo(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_Deactivate(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String categoryId = request.getQueryParameters().get("id");

        UserDB.fnUser_Deactivate(connectionString, categoryId);
        return null;
    }

}
