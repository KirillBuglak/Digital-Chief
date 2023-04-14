package digitalTest.services;

import digitalTest.config.Messages;
import digitalTest.entities.Department;
import digitalTest.entities.enums.Jurisdiction;
import digitalTest.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public String createOrUpdate(JSONObject object) {
        boolean update = false;
        Department dep;
        try {
            dep = departmentRepository.findById((Integer) object.get("id")).orElseThrow();
            update = true;
        } catch (RuntimeException e) {
            dep = new Department();
            try {
                if ((Integer) object.get("id") < 1) {
                    throw new RuntimeException();
                }
                dep.setId((Integer) object.get("id"));
            } catch (RuntimeException ex) {
                return Messages.INCORRECT;
            }
        }
        setDetailsAndSave(object, dep);
        return update ? "Updated" : "Created";
    }

    public Object getDepartment(int id) {
        try {
            return departmentRepository.findById(id).orElseThrow();
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
    }

    public String deleteDepartment(int id) {
        try {
            Department dep = departmentRepository.findById(id).orElseThrow();
            departmentRepository.delete(dep);
            return "Department with id=" + id + " is deleted";
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
    }

    public String updateDepartment(int id, JSONObject object) {
        try {
            Department dep = departmentRepository.findById(id).orElseThrow();
            setDetailsAndSave(object, dep);
            return "Department updated";
        } catch (RuntimeException e) {
            return Messages.INCORRECT;
        }
    }

    private void setDetailsAndSave(JSONObject object, Department dep) {
        dep.setCode((Integer) object.get("code"));
        dep.setName((String) object.get("name"));
        dep.setAddress((String) object.get("address"));
        dep.setJurisdiction(Jurisdiction.valueOf((String) object.get("jurisdiction")));
        departmentRepository.save(dep);
    }

    public String deleteDepartments() {
        if (departmentRepository.findAll().size() != 0) {
            departmentRepository.deleteAll();
            return Messages.ALL_CLEARED;
        }
        return Messages.EMPTY_TABLE;
    }
}
