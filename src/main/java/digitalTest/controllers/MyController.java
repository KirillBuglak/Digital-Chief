package digitalTest.controllers;

import digitalTest.entities.Department;
import digitalTest.entities.Employee;
import digitalTest.services.DepartmentService;
import digitalTest.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MyController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable int id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @DeleteMapping("/employees")
    public String deleteEmployees() {
        return employeeService.deleteEmployees();
    }
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }
    @DeleteMapping("/departments")
    public String deleteDepartments() {
        return departmentService.deleteDepartments();
    }
    @DeleteMapping("/departments/{id}")
    public String deleteDepartment(@PathVariable int id) {
        return departmentService.deleteDepartment(id);
    }

    @PostMapping("/departments")
    public ResponseEntity<String> postDepartments(@RequestBody JSONObject object) {
        return ResponseEntity.ok(departmentService.createOrUpdate(object));
    }

    @PostMapping("/employees")
    public ResponseEntity<String> postEmployees(@RequestBody JSONObject object) {
        return ResponseEntity.ok(employeeService.createOrUpdate(object));
    }

    @PostMapping("/employees/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody JSONObject object) {
        return employeeService.updateEmployee(id, object);
    }

    @PostMapping("/departments/{id}")
    public String updateDepartment(@PathVariable int id, @RequestBody JSONObject object) {
        return departmentService.updateDepartment(id, object);
    }
}
