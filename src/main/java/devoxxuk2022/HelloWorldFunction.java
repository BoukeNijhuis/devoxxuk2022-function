package devoxxuk2022;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class HelloWorldFunction {
    // example call <host>/api/hello?name=x
    @FunctionName("Hello")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        final String name = request.getQueryParameters().get("name");
        final String reply = handleRequest(name);

        return request.createResponseBuilder(HttpStatus.OK).body(reply).build();
    }

    private String handleRequest(String name) {
        if (name == null || name.length() == 0) {
            name = "World";
        }

        return String.format("Hello %s!", name);
    }
}
