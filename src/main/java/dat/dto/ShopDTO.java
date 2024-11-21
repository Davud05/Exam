package dat.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {
    private int id;
    private String name;
    private String url;
    private List<String> categories;
}