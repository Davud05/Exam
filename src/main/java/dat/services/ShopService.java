package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dto.ShopResponse;
import dat.utils.Utils;
import java.util.List;
import dat.dto.ShopDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShopService {
    private static final String BASE_URL = "https://shopapi.cphbusinessapps.dk/shops";
    private final ObjectMapper objectMapper = new Utils().getObjectMapper();

    public ShopResponse getShops() {
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            // Check the status code and parse response
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), ShopResponse.class);
            } else {
                throw new RuntimeException("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching shops: " + e.getMessage());
        }
    }
    // Tilføj denne nye metode her
    public List<ShopDTO> getShopsByCategory(String category) {
        return getShops().getShops().stream()
                .filter(shop -> shop.getCategories().contains(category.toLowerCase()))
                .toList();
    }
}