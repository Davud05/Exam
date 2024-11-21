package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private double purchasePrice;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    private LocalDate acquisitionDate;
    private String description;
    
    @ManyToOne
    @ToString.Exclude
    private Student student;
    
    // Tilføjer specifik setter for student
    public void setStudent(Student student) {
        this.student = student;
    }
} 