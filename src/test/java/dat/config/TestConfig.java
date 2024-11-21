package dat.config;

import dat.routes.ItemRoutes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import static io.javalin.apibuilder.ApiBuilder.path;

public class TestConfig {
    public static Javalin getTestApp() {
        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("items", ItemRoutes.getRoutes());
            });
            config.router.contextPath = "/api/v1";
        });
    }
}