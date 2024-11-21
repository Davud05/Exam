package dat.routes;

import dat.controllers.ItemController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ItemRoutes {

    public static EndpointGroup getRoutes() {
        return () -> {
            get(ItemController::getAllItems);
            post(ItemController::createItem);

            // Specifikke paths først
            path("student-totals", () -> {
                get(ItemController::getStudentItemTotals);
            });

            path("category/{category}", () -> {
                get(ItemController::getItemsByCategory);
            });

            path("shops", () -> {
                get(ItemController::getAllShops);
            });

            // Tilføj denne som en separat path
            path("shops/{category}", () -> {
                get(ItemController::getShopsByCategory);
            });

            path("populate", () -> {
                post(ItemController::populateDatabase);
            });

            // Generiske ID paths til sidst
            path("{id}", () -> {
                get(ItemController::getItemById);
                put(ItemController::updateItem);
                delete(ItemController::deleteItem);
            });

            path("{itemId}/students/{studentId}", () -> {
                put(ItemController::assignItemToStudent);
            });
        };
    }}