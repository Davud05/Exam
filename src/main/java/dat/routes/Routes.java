package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    // Import new routes here
    public static EndpointGroup getRoutes() {
        return () -> {
            path("/items", ItemRoutes.getRoutes());
            // Add other routes here if needed
        };
    }
}
