package dat.config;

import dat.dao.ItemDAO;
import dat.dao.StudentDAO;
import dat.entities.Category;
import dat.entities.Item;
import dat.entities.Student;

import java.time.LocalDate;

public class Populator {
    private static final ItemDAO itemDAO = new ItemDAO();
    private static final StudentDAO studentDAO = new StudentDAO();
    
    public static void main(String[] args) {
        // Opret studerende
        Student s1 = Student.builder()
                .name("Anders And")
                .email("anders@and.dk")
                .phone("12345678")
                .enrollmentDate(LocalDate.now())
                .build();
        
        Student s2 = Student.builder()
                .name("Mickey Mouse")
                .email("mickey@mouse.dk")
                .phone("87654321")
                .enrollmentDate(LocalDate.now())
                .build();
                
        studentDAO.create(s1);
        studentDAO.create(s2);
        
        // Opret items
        Item i1 = Item.builder()
                .name("Sony Camera")
                .category(Category.VIDEO)
                .purchasePrice(5999.99)
                .acquisitionDate(LocalDate.now())
                .description("Professional video camera")
                .build();
                
        Item i2 = Item.builder()
                .name("VR Headset")
                .category(Category.VR)
                .purchasePrice(3999.99)
                .acquisitionDate(LocalDate.now())
                .description("Oculus Quest 2")
                .build();
                
        itemDAO.create(i1);
        itemDAO.create(i2);
        
        // Tilknyt items til studerende
        itemDAO.addItemToStudent(i1.getId(), s1.getId());
        itemDAO.addItemToStudent(i2.getId(), s2.getId());
    }
} 