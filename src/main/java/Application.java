public class Application {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee newPersonal = new Employee("Joanne", "Rowling", "female", 57, 1);
        employeeDAO.create(newPersonal);

        System.out.println(employeeDAO.readById(20));

        employeeDAO.updateGenderById(19, "female");
        System.out.println(employeeDAO.readById(19));

        employeeDAO.deleteById(19);
        for (Employee e : employeeDAO.readAll()) {
            System.out.println(e);
        }
    }
}
