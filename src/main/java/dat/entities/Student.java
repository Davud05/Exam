package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String email;
    private LocalDate enrollmentDate;
    private String phone;
    
    @Builder.Default
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
    
    public void addItem(Item item) {
        items.add(item);
        item.setStudent(this);
    }
} 