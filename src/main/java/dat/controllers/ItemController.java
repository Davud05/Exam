package dat.controllers;

import dat.dao.ItemDAO;
import dat.dto.ItemDTO;
import dat.dto.ShopDTO;
import dat.entities.Item;
import dat.services.ShopService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import dat.dto.ShopResponse;

public class ItemController {
    private static final ItemDAO itemDAO = new ItemDAO();
    private static final ShopService shopService = new ShopService();

    // GET: "/items" - Get all items
    public static void getAllItems(Context ctx) {
        var items = itemDAO.readAll()
                .stream()
                .map(ItemDTO::from)
                .toList();
        ctx.json(items);
    }

    public static void getItemById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        itemDAO.read(id)
                .map(item -> {
                    // Hent shops for item's kategori
                    List<ShopDTO> relevantShops = shopService.getShopsByCategory(
                            item.getCategory().toString()
                    );
                    return ItemDTO.fromWithStudentAndShops(item, relevantShops);
                })
                .ifPresentOrElse(
                        ctx::json,
                        () -> {
                            ctx.status(HttpStatus.NOT_FOUND);
                            ctx.json(Map.of(
                                    "message", "Item with ID: " + id + " not found",
                                    "path", ctx.path()
                            ));
                        }
                );
    }
    // GET: "/items/category/{category}" - Get items by category
    public static void getItemsByCategory(Context ctx) {
        String category = ctx.pathParam("category").toUpperCase();
        var items = itemDAO.readAll()
                .stream()
                .filter(item -> item.getCategory().toString().equals(category))
                .map(ItemDTO::from)
                .toList();
        ctx.json(items);
    }
    // GET: "/items/student-totals" - Get total purchase price per student
    public static void getStudentItemTotals(Context ctx) {
        var studentTotals = itemDAO.readAll().stream()
                .filter(item -> item.getStudent() != null)  // Kun items der er udlånt
                .collect(Collectors.groupingBy(
                        item -> item.getStudent().getId(),
                        Collectors.summingDouble(Item::getPurchasePrice)
                ))
                .entrySet().stream()
                .map(entry -> Map.of(
                        "studentId", entry.getKey(),
                        "totalPurchasePrice", entry.getValue()
                ))
                .toList();

        ctx.json(studentTotals);
    }


    // POST: "/items" - Add a new item
    public static void createItem(Context ctx) {
        Item item = ctx.bodyAsClass(Item.class);
        Item created = itemDAO.create(item);
        ctx.status(HttpStatus.CREATED).json(ItemDTO.from(created));
    }

    // PUT: "/items/{id}" - Update an item
    public static void updateItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Item item = ctx.bodyAsClass(Item.class);
        Item updated = itemDAO.update(id, item);
        ctx.json(ItemDTO.from(updated));
    }

    public static void deleteItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<Item> item = itemDAO.read(id);

        if (item.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of(
                    "message", "Cannot delete - item with ID: " + id + " not found",
                    "path", ctx.path()
            ));
            return;
        }

        itemDAO.delete(id);
        ctx.status(HttpStatus.NO_CONTENT);
    }

    // PUT: "/items/{itemId}/students/{studentId}" - Assign an item to a student
    public static void assignItemToStudent(Context ctx) {
        int itemId = Integer.parseInt(ctx.pathParam("itemId"));
        int studentId = Integer.parseInt(ctx.pathParam("studentId"));
        itemDAO.addItemToStudent(itemId, studentId);
        ctx.status(HttpStatus.NO_CONTENT);
    }
    // GET: "/items/shops/{category}" - Get shops by category
    public static void getShopsByCategory(Context ctx) {
        String category = ctx.pathParam("category");
        var shops = shopService.getShopsByCategory(category);
        ctx.json(Map.of("shops", shops));
    }

    // POST: "/items/populate" - Populate the database
    public static void populateDatabase(Context ctx) {
        // Implementation af database population
        // Dette kunne være at oprette nogle test-items
        ctx.status(HttpStatus.CREATED);
    }
    public static void getAllShops(Context ctx) {
        ShopResponse shops = shopService.getShops();
        ctx.json(shops);
    }
} 