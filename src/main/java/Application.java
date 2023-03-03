import java.sql.*;

public class Application {
    private final static String USER = "postgres";
    private final static String PASSWORD = "qwe123";
    private final static String URL = "jdbc:postgresql://localhost:5432/skypro";
    private final static String REQUEST = "SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id WHERE id = (?)";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(REQUEST)) {
            statement.setInt(1, 3);
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String city = resultSet.getString("city_name");

                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(gender);
                System.out.println("age = " + age + " years.");
                System.out.println(city);


                EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);
                System.out.println(employeeDAO.readById(1));
                for (Employee empl: employeeDAO.readAll() ) {
                    System.out.println(empl);
                }
                employeeDAO.updateGenderById(1,"male");
                System.out.println(employeeDAO.readById(1));

                // если id города нет в соответствующей таблице то при добавлении пользователя прокидывает исключение, что ожидаемо.
                Employee newPersonal = new Employee("Freddie", "Mercury", "male", 77,
                        new City(4, "Kazan"));
               // /* //замутил чтобы при пробных запусках не плодить лишних записей в базе. Оба метода работают.
                employeeDAO.create(newPersonal);
                employeeDAO.deleteById(16);
                System.out.println("***");
                for (Employee empl: employeeDAO.readAll() ) {
                    System.out.println(empl);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
