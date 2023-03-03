import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Employee employee) {
        String request =
                "INSERT INTO employee (first_name, last_name, gender, age, city_id) VALUES ((?), (?), (?), (?), (?))";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity().getId());
            statement.executeQuery();
        }catch (SQLTimeoutException e1){
            System.out.println("timeout");
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public Employee readById(int id) {
        Employee result = new Employee();
        String request =
                "SELECT * FROM employee INNER JOIN city ON employee.city_id=city.city_id AND id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setFirstName(resultSet.getString("first_name"));
                result.setLastName(resultSet.getString("last_name"));
                result.setGender(resultSet.getString("gender"));
                result.setAge(resultSet.getInt("age"));
                result.setCity(new City(resultSet.getInt("city_id"),
                        resultSet.getString("city_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Employee> readAll() {
        List<Employee> resultList = new LinkedList<>();
        String request =
                "SELECT * FROM employee INNER JOIN city ON employee.city_id=city.city_id";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                City city = new City(resultSet.getInt("city_id"),
                        resultSet.getString("city_name"));
                resultList.add(new Employee(id, firstName, lastName, gender, age, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void updateGenderById(int id, String gender) {
        String request = "UPDATE employee SET gender=(?) WHERE id=(?)";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, gender);
            statement.setInt(2, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String request = "DELETE FROM employee WHERE id=(?)";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
