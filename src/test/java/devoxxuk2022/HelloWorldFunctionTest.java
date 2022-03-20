package devoxxuk2022;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class HelloWorldFunctionTest {

    @Test
    void testNoQueryParam() {
        Assertions.assertEquals("Hello World!", callFunction(null));
    }

    @Test
    void testEmptyQueryParam() {
        Assertions.assertEquals("Hello World!", callFunction(""));
    }

    @Test
    void testFilledQueryParam() {
        Assertions.assertEquals("Hello Devoxx!", callFunction("Devoxx"));
    }

    private String callFunction(String input) {

        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        if (input != null) {
            queryParams.put("name", input);
        }
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer((Answer<HttpResponseMessage.Builder>) invocation -> {
            HttpStatus status = (HttpStatus) invocation.getArguments()[0];
            return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        final HttpResponseMessage ret = new HelloWorldFunction().run(req, context);
        return (String) ret.getBody();
    }
}