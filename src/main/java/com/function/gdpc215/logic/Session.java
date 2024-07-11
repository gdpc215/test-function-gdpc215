package com.function.gdpc215.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.UserDB;
import com.function.gdpc215.model.UserEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Session {
  static String jwtSecretKey;
  static int jwtTokenTimeout;

  static {
    // Generate token
    // String jwtSecretKey = System.getenv("KV-JWT-SECRET-KEY");
    // Token: node -e
    // "console.log(require('crypto').randomBytes(32).toString('hex'))"
    jwtSecretKey = "4daa4c364d60cfec51533d8fe5065ddac8c9a03e971e34a874f96c55b2bb27cc";
    // int jwtTokenTimeout =
    // Integer.parseInt(System.getenv("KV-JWT-TOKEN-TIMEOUT"));
    jwtTokenTimeout = 1000 * 60 * 60 * 1; // 1 hour = 3600000
  }

  public static Object hubLogin(String subRoute, HttpRequestMessage<Optional<String>> request,
      String connectionString) throws Exception {
    return switch (subRoute) {
      case "get-token" -> fnSession_GetToken(request, connectionString);
      case "validate-token" -> fnSession_ValidateToken(request, connectionString);
      case "login" -> fnSession_Login(request, connectionString);
      case "register" -> fnSession_Register(request, connectionString);

      default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
    };
  }

  private static Object fnSession_GetToken(HttpRequestMessage<Optional<String>> request, String connectionString)
      throws Exception {
    // Read request body
    Optional<String> body = request.getBody();
    if (SecurityUtils.isValidRequestBody(body)) {
      // Parse JSON request body
      JSONObject jsonBody = new JSONObject(body.get());
      String strEmail = jsonBody.optString("strEmail");
      String strPassword = jsonBody.optString("strPassword");

      UserEntity entity = UserDB.fnUser_LoginAttemptWithEmail(connectionString, strEmail);
      if (entity != null) {
        String hashedPassword = UserUtils.getHashedPassword(strPassword, entity.strPasswordSalt);
        if (entity.strPassword.equals(hashedPassword)) {

          return JwtUtil.generateToken(entity, jwtTokenTimeout, jwtSecretKey);
        }
      }
      // Si llega aqui, alguno de los datos es incorrecto.
      return request
          .createResponseBuilder(HttpStatus.UNAUTHORIZED)
          .body("Los datos ingresados no son correctos.")
          .build();

    } else {
      throw new JSONException("Error al leer el cuerpo de la peticion");
    }
  }

  /**
   * Funcion para validar y refrescar tokens de sesion. Completado.
   */
  private static Object fnSession_ValidateToken(HttpRequestMessage<Optional<String>> request, String connectionString)
      throws Exception {
    // Read request body
    Optional<String> body = request.getBody();
    if (SecurityUtils.isValidRequestBody(body)) {
      JSONObject jsonBody = new JSONObject(body.get());
      String token = jsonBody.getJSONObject("token").optString("token");

      if (token != null && !token.equals("")) {
        // Token is not null nor empty. Identify user and token validity
        UserEntity user = UserDB.fnUser_GetById(connectionString, JwtUtil.getUserId(token, jwtSecretKey));

        if (user != null && user.id.equals("")) {
          // Usuario valido
          if (!user.flgGhostUser && JwtUtil.isTokenExpired(token, jwtSecretKey)) {
            // Usuario no es ghost y se vencio la validez del token. De nuevo al login.
            return request
                .createResponseBuilder(HttpStatus.UNAUTHORIZED)
                .body("El token se encuentra vencido.")
                .build();
          }
          /**
           * Si llega aqui:
           * - El usuario es ghost.
           * - El usuario no es ghost, pero el token aun es valido
           * ACCION: Enviar token nuevo con usuario existente.
           */
          JSONObject obj = new JSONObject();
          obj.put("token", JwtUtil.generateToken(user, jwtTokenTimeout, jwtSecretKey));
          obj.put("userId", user.id);
          return obj.toMap();
        }
      }
      /**
       * Si llega aqui:
       * - El token es nulo o vacio
       * - El token no esta vacio, pero el usuario no existe / no es valido
       * ACCION: Enviar nuevo token, con ghost user
       */
      UserEntity newUser = UserDB.fnUser_CreateGhost(connectionString);
      JSONObject obj = new JSONObject();
      obj.put("token", JwtUtil.generateToken(newUser, jwtTokenTimeout, jwtSecretKey));
      obj.put("userId", newUser.id);
      return obj.toMap();
    } else {
      throw new JSONException("Error al leer el cuerpo de la peticion");
    }
  }
  
  private static Object fnSession_Login(HttpRequestMessage<Optional<String>> request, String connectionString)
      throws Exception {
    // Read request body
    Optional<String> body = request.getBody();
    if (SecurityUtils.isValidRequestBody(body)) {
      JSONObject jsonBody = new JSONObject(body.get());
      UserEntity entity = new UserEntity(jsonBody);

      UserEntity hashedEntity = UserUtils.performHashAndSalt(entity);
      UserDB.fnUser_UpdateCredentials(connectionString, hashedEntity);
      return null;
    } else {
      throw new JSONException("Error al leer el cuerpo de la peticion");
    }
  }

  private static Object fnSession_Register(HttpRequestMessage<Optional<String>> request, String connectionString)
      throws Exception {
    // Read request body
    Optional<String> body = request.getBody();
    if (SecurityUtils.isValidRequestBody(body)) {
      JSONObject jsonBody = new JSONObject(body.get());
      UserEntity entity = new UserEntity(jsonBody);

      UserEntity hashedEntity = UserUtils.performHashAndSalt(entity);
      UserDB.fnUser_UpdateCredentials(connectionString, hashedEntity);
      return null;
    } else {
      throw new JSONException("Error al leer el cuerpo de la peticion");
    }
  }

  public class JwtUtil {
    public static String generateToken(UserEntity userEntity, int timeOut, String secretKey) {
      return Jwts
          .builder()
          .setSubject(userEntity.id)
          .claim("isGhostUser", userEntity.flgGhostUser)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
    }

    public static String getUserId(String token, String secretKey) {
      return extractClaims(token, secretKey).getSubject();
    }

    public static boolean isTokenExpired(String token, String secretKey) {
      return !extractClaims(token, secretKey).getExpiration().before(new Date());
    }

    public static boolean isGhostUser(String token, String secretKey) {
      return (boolean) extractClaims(token, secretKey).get("isGhostUser");
    }

    public static boolean isTokenValid(String token, String secretKey, String userId) {
      return getUserId(token, secretKey).equals(userId) && isTokenExpired(token, secretKey);
    }

    private static Claims extractClaims(String token, String secretKey) {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    }
  }

  public class UserUtils {
    /** Utility functions **/
    public static UserEntity performHashAndSalt(UserEntity user) throws NoSuchAlgorithmException {
      String salt = getSalt();
      String hashedPassword = getHashedPassword(user.strPassword, salt);

      user.strPassword = hashedPassword;
      user.strPasswordSalt = salt;

      return user;
    }

    // Generates a random salt
    public static String getSalt() throws NoSuchAlgorithmException {
      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
      byte[] salt = new byte[16];
      sr.nextBytes(salt);
      return Base64.getEncoder().encodeToString(salt);
    }

    // Hashes the password with the given salt
    public static String getHashedPassword(String password, String salt) throws NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes());
      byte[] bytes = md.digest(password.getBytes());
      return Base64.getEncoder().encodeToString(bytes);
    }
  }
}