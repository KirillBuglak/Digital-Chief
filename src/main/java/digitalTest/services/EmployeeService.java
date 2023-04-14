package digitalTest.services;

import digitalTest.config.Messages;
import digitalTest.entities.Department;
import digitalTest.entities.Employee;
import digitalTest.entities.enums.Position;
import digitalTest.repositories.DepartmentRepository;
import digitalTest.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public String createOrUpdate(JSONObject object) {
        boolean update = false;
        Employee emp;
        try {
            emp = employeeRepository.findById((Integer) object.get("id")).orElseThrow();
            update = true;
        } catch (RuntimeException e) {
            emp = new Employee();
            try{
                if ((Integer) object.get("id") < 1) {
                    throw new RuntimeException();
                }
                emp.setId((Integer) object.get("id"));}
            catch (RuntimeException ex){
                return Messages.INCORRECT;
            }
        }
        String error = setDetailsAndSave(object, emp);
        if (error != null) return error;
        return update ? "Updated" : "Created";
    }

    public Object getEmployee(int id) {
        try {
            return employeeRepository.findById(id).orElseThrow();
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
    }

    public String deleteEmployee(int id) {
        try {
            Employee emp = employeeRepository.findById(id).orElseThrow();
            employeeRepository.delete(emp);
            return "Employee with id=" + id + " is deleted";
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
    }

    public String updateEmployee(int id, JSONObject object) {
        Employee emp;
        try {
            emp = employeeRepository.findById(id).orElseThrow();
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
        String error = setDetailsAndSave(object, emp);
        if (error != null) return error;
        return "Employee updated";
    }

    private String setDetailsAndSave(JSONObject object, Employee emp) {
        try {
            emp.setLastName((String) object.get("lastName"));
            emp.setFirstName((String) object.get("firstName"));
            emp.setSalary((Double) object.get("salary"));
            emp.setPosition(Position.valueOf((String) object.get("position")));
            Department departmentId = departmentRepository.findById((Integer) object.get("departmentId")).orElseThrow();
            emp.setDepartment(departmentId);
            employeeRepository.save(emp);
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
        return null;
    }

    public String deleteEmployees() {
        if (employeeRepository.findAll().size() != 0) {
            employeeRepository.deleteAll();
            return Messages.ALL_CLEARED;
        }
        return Messages.EMPTY_TABLE;
    }
}
