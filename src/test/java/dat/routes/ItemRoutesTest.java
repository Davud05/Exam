package dat.routes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.TestConfig;
import dat.dao.ItemDAO;
import dat.entities.Category;
import dat.entities.Item;
import dat.utils.Utils;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemRoutesTest {
    private static final ObjectMapper MAPPER = new Utils().getObjectMapper();
    private static ItemDAO itemDAO;
    private Item testItem;

    @BeforeEach
    void setUp() {
        itemDAO = new ItemDAO();
        testItem = Item.builder()
                .name("Test Camera")
                .purchasePrice(4999.99)
                .category(Category.VIDEO)
                .acquisitionDate(LocalDate.now())
                .description("Test description")
                .build();
    }

    @Test
    void getItemByIdShouldReturnItemWithShops() {
        // Arrange
        Item savedItem = itemDAO.create(testItem);

        // Act & Assert
        JavalinTest.test(TestConfig.getTestApp(), (server, client) -> {
            var response = client.get("/api/v1/items/" + savedItem.getId());
            assertEquals(200, response.code());

            var json = MAPPER.readTree(response.body().string());

            // Check item details
            assertEquals(savedItem.getName(), json.get("name").asText());
            assertEquals(savedItem.getCategory().toString(), json.get("category").asText());

            // Check that shops are included and not empty
            assertTrue(json.has("relevantShops"));
            assertTrue(json.get("relevantShops").isArray());
            assertFalse(json.get("relevantShops").isEmpty());

            // Check that shops have correct category
            var shops = json.get("relevantShops");
            shops.forEach(shop ->
                    assertTrue(shop.get("categories").toString().toLowerCase()
                            .contains(savedItem.getCategory().toString().toLowerCase()))
            );
        });
    }
}