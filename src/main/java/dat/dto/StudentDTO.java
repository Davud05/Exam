package dat.dto;

import dat.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    private int id;
    private String name;
    private String email;
    private LocalDate enrollmentDate;
    private String phone;
    
    public static StudentDTO from(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .enrollmentDate(student.getEnrollmentDate())
                .phone(student.getPhone())
                .build();
    }
} 