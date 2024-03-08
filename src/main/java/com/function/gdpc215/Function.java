package com.function.gdpc215;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        //final String query = request.getQueryParameters().get("name");
        //final String name = request.getBody().orElse(query);

        final String name = request.getQueryParameters().get("name");
        final String id = request.getQueryParameters().get("id");

        if (id != null) {
            return request.createResponseBuilder(HttpStatus.OK).body(PersonData.getNewPerson(id)).build();
        } if (name != null) {
            return request.createResponseBuilder(HttpStatus.OK).body("Hola, " + name).build();
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        }
    }

    static class PersonData {
        public static PersonData getNewPerson(String id) {
            try {
                int idInt = Integer.parseInt(id);
                return new PersonData(
                    "Nombre " + id, 
                    10.0 * idInt, 
                    (idInt % 3 == 0), 
                    id, 
                    "Persona " + id
                );
            } catch (Exception e) {
                return new PersonData("Persona por defecto", 0.0, true, "0", "Persona por defecto");
            }
        }
        public PersonData (String name, Double price, Boolean isNew, String imageId, String description) {
            this.name = name;
            this.price = price;
            this.isNew = isNew;
            this.imageId = imageId;
            this.description = description;
        }

        public String name;
        public Double price;
        public Boolean isNew;
        public String imageId;
        public String description;
    }
}
