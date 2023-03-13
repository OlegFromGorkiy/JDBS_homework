import java.util.List;

public interface EmployeeDAO {
    void create(Employee employee);

    Employee readById(int id);

    List<Employee> readAll();

    void updateGenderById(int id, String gender);

    void deleteById(int id);
}
