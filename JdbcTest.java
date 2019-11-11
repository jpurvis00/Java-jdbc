/* This was my first java sql connection attempt.  I created a simple mySQL table, attempted to connect and 
*  outputted the first and last names of the employees.  This ran successfully.
*/
import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try{
            //Get connection to db
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "student", "student");
            System.out.println("DB connection successful!");

            //Create a statement
            myStmt = myConn.createStatement();

            //Execute sql query
            myRs = myStmt.executeQuery("select * from employees");

            //Process results
            while(myRs.next()){
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }

            displayEmployee(myStmt, "Eric", "Wright");

            //Insert data into db
            int rowsAffected = myStmt.executeUpdate("insert into employees (last_name, first_name, email, department, salary) values ('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.00)");

            displayEmployee(myStmt, "Eric", "Wright");

            //Update data in db
            int rowsAffected2 = myStmt.executeUpdate("update employees set email='eric.wright@foobar.com' where last_name='Wright' and first_name='Eric'");

            displayEmployee(myStmt, "Eric", "Wright");

            //Delete info from the db
            int rowsAffected3 = myStmt.executeUpdate("delete from employees where last_name='Wright' and first_name='Eric'");

            displayEmployee(myStmt, "Eric", "Wright");

            displaySalary(myConn);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(myRs != null)
                myRs.close();
        }
    }

    /*Returns the results of the employee we are querying*/
    public static void displayEmployee(Statement stmt, String firstName, String lastName) throws SQLException{
        boolean found = false;
        ResultSet results = stmt.executeQuery("select * from employees where last_name='" + lastName + "' and first_name='" + firstName + "'");

        /*Cycle through all results returned and print first name, last name and email*/
        while (results.next()) {
            System.out.println(results.getString("first_name") + ", " + results.getString("last_name") + " - " + results.getString("email"));
            found = true;
        }

        /*No results were found*/
        if(!found)
            System.out.println("Employee not found");
    }

    /*Method is using prepared statement instead of hard coding sql statements.  ? takes the place of hard coded
    * values.  */
    public static void displaySalary(Connection myConn) throws SQLException{
        boolean found = false;
        PreparedStatement myStmt = myConn.prepareStatement("select * from employees where salary > ? and department=?");

        /*Replaces first ? in above statement*/
        myStmt.setDouble(1, 80000);
        /*Replaces second ? in above statement*/
        myStmt.setString(2, "Legal");

        /*Execute the prepared statement*/
        ResultSet results = myStmt.executeQuery();

        while(results.next()){
            System.out.println(results.getString("first_name") + ", " + results.getString("last_name") + " - " + results.getString("salary") + ", " + results.getString("department"));
            found = true;
        }

        /*No results were found*/
        if(!found)
            System.out.println("No matching data");
    }
}


/* In order for this to work, we had to add the mysql connector .jar file to our project.  This was done 
* by downloading the jar file and then adding it to our project.  Following steps are for intellij
* 1. File -> Project Structure
* 2. Project Settings > Modules > Dependencies > "+" sign > JARs or directories...
* 3. Select the jar file and click on OK, then click on another OK button to confirm
* 4. You can view the jar file in the "External Libraries" folder
*/
