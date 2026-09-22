
package employee.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeManagementSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employees by Department");
            System.out.println("5. Add Department");
            System.out.println("6. Update Employee Information");
            System.out.println("7. Update Employee Salary");
            System.out.println("8. Delete Employee");
            System.out.println("9. View Employees by Salary");
            System.out.println("10. Total Number of Employees");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewAllEmployees();
                    break;

                case 3:
                    searchEmployeeById();
                    break;

                case 4:
                    searchByDepartment();
                    break;

                case 5:
                    addDepartment();
                    break;

                case 6:
                    updateEmployee();
                    break;

                case 7:
                    updateSalary();
                    break;

                case 8:
                    deleteEmployee();
                    break;

                case 9:
                    viewEmployeesBySalary();
                    break;

                case 10:
                    totalEmployees();
                    break;

                case 0:
                    System.out.println("Application closed.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }

    // 1. Add a new employee
    static void addEmployee() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO Employee " +
                    "(name, email, phone, salary, department_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            sc.nextLine();

            System.out.print("Enter employee name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            System.out.print("Enter salary: ");
            double salary = sc.nextDouble();

            System.out.print("Enter department ID: ");
            int departmentId = sc.nextInt();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setDouble(4, salary);
            ps.setInt(5, departmentId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee added successfully.");
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. View all employees with department names
    static void viewAllEmployees() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT e.employee_id, e.name, e.email, " +
                    "e.phone, e.salary, d.department_name, e.status " +
                    "FROM Employee e LEFT JOIN Department d " +
                    "ON e.department_id = d.department_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nID\tName\t\tSalary\tDepartment\tStatus");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("employee_id") + "\t" +
                    rs.getString("name") + "\t\t" +
                    rs.getDouble("salary") + "\t" +
                    rs.getString("department_name") + "\t" +
                    rs.getString("status")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Search employee by ID
    static void searchEmployeeById() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM Employee WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter employee ID: ");
            int id = sc.nextInt();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("Employee ID: " +
                        rs.getInt("employee_id"));

                System.out.println("Name: " +
                        rs.getString("name"));

                System.out.println("Email: " +
                        rs.getString("email"));

                System.out.println("Phone: " +
                        rs.getString("phone"));

                System.out.println("Salary: " +
                        rs.getDouble("salary"));

                System.out.println("Department ID: " +
                        rs.getInt("department_id"));

                System.out.println("Status: " +
                        rs.getString("status"));

            } else {
                System.out.println("Employee not found.");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. Search employees by department
    static void searchByDepartment() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT e.employee_id, e.name, e.salary, " +
                    "d.department_name FROM Employee e " +
                    "JOIN Department d ON e.department_id = d.department_id " +
                    "WHERE d.department_name = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            sc.nextLine();

            System.out.print("Enter department name: ");
            String department = sc.nextLine();

            ps.setString(1, department);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getInt("employee_id") + " " +
                    rs.getString("name") + " " +
                    rs.getDouble("salary") + " " +
                    rs.getString("department_name")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5. Add a department
    static void addDepartment() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO Department " +
                    "(department_name, location) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            sc.nextLine();

            System.out.print("Enter department name: ");
            String name = sc.nextLine();

            System.out.print("Enter location: ");
            String location = sc.nextLine();

            ps.setString(1, name);
            ps.setString(2, location);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Department added successfully.");
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 6. Update employee information
    static void updateEmployee() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE Employee SET name = ?, " +
                    "email = ?, phone = ? WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter employee ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter new name: ");
            String name = sc.nextLine();

            System.out.print("Enter new email: ");
            String email = sc.nextLine();

            System.out.print("Enter new phone: ");
            String phone = sc.nextLine();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 7. Update employee salary
    static void updateSalary() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE Employee SET salary = ? " +
                    "WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter employee ID: ");
            int id = sc.nextInt();

            System.out.print("Enter new salary: ");
            double salary = sc.nextDouble();

            ps.setDouble(1, salary);
            ps.setInt(2, id);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Salary updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 8. Delete an employee
    static void deleteEmployee() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM Employee WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter employee ID: ");
            int id = sc.nextInt();

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 9. View employees earning more than a salary
    static void viewEmployeesBySalary() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM Employee WHERE salary > ?";

            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter minimum salary: ");
            double salary = sc.nextDouble();

            ps.setDouble(1, salary);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getInt("employee_id") + " " +
                    rs.getString("name") + " " +
                    rs.getDouble("salary")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 10. Total number of employees
    static void totalEmployees() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total FROM Employee";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Total employees: " +
                        rs.getInt("total"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}