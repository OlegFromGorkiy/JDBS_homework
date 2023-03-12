public class Application {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee newPersonal = new Employee("Joanne", "Rowling", "female", 57, new City("London"));
        employeeDAO.create(newPersonal);

        }
    }

// SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id;