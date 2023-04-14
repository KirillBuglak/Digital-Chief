package digitalTest.entities;

import digitalTest.entities.enums.Position;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {
    @Id
    private int id;
    private String lastName;
    private String firstName;
    private double salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    @ManyToOne
    private Department department;
}
