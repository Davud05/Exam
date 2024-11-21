package dat.dto;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopResponse {
    private List<ShopDTO> shops;
}