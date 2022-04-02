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

    private static final String DEFAULT_VALUE = "World";

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
        final String reply = handleInput(name);

        return request.createResponseBuilder(HttpStatus.OK).body(reply).build();
    }

    private String handleInput(String input) {
        String name = DEFAULT_VALUE;

        if (input != null && input.length() != 0) {
            name = input;
        }

        return String.format("Hello %s!", name);
    }
}
