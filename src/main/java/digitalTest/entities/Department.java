package digitalTest.entities;

import digitalTest.entities.enums.Jurisdiction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Department {
    @Id
    private int id;
    private int code;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private Jurisdiction jurisdiction;
}
