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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

/* In order for this to work, we had to add the mysql connector .jar file to our project.  This was done 
* by downloading the jar file and then adding it to our project.  Following steps are for intellij
* 1. File -> Project Structure
* 2. Project Settings > Modules > Dependencies > "+" sign > JARs or directories...
* 3. Select the jar file and click on OK, then click on another OK button to confirm
* 4. You can view the jar file in the "External Libraries" folder
*/
