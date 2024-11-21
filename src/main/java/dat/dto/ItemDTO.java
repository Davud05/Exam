package dat.dto;

import dat.entities.Category;
import dat.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private int id;
    private String name;
    private double purchasePrice;
    private Category category;
    private LocalDate acquisitionDate;
    private String description;
    private StudentDTO student;
    private List<ShopDTO> relevantShops;

    public static ItemDTO from(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .purchasePrice(item.getPurchasePrice())
                .category(item.getCategory())
                .acquisitionDate(item.getAcquisitionDate())
                .description(item.getDescription())
                .build();
    }

    public static ItemDTO fromWithStudent(Item item) {
        ItemDTO dto = from(item);
        if (item.getStudent() != null) {
            dto.student = StudentDTO.from(item.getStudent());
        }
        return dto;
    }
    public static ItemDTO fromWithStudentAndShops(Item item, List<ShopDTO> shops) {
        ItemDTO dto = fromWithStudent(item);
        dto.relevantShops = shops;
        return dto;
    }
} 