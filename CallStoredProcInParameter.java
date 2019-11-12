import jdk.nashorn.internal.codegen.CompilerConstants;

import java.sql.*;

public class CallStoredProcInParameter {
    public static void main(String[] args) throws Exception{
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            /*Create connection to db*/
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "student", "student");

            String department = "Engineering";
            int increaseAmt = 10000;

            System.out.println("Salaries BEFORE");
            showSalaries(myConn, department);

            /*Prepare stored procedure call*/
            myStmt = myConn.prepareCall("{call increase_salaries_for_department(?, ?)}");

            /*Replace ? parameters in above call*/
            myStmt.setString(1, department);
            myStmt.setDouble(2, 10000);

            /*Called stored procedure*/
            System.out.println("\n\nCalling stored procedure.  increase_salaries_for_department('" + department + "', '"  + increaseAmt + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            System.out.println("\n\nSalaries AFTER");
            showSalaries(myConn, department);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myConn.close();
        }
    }

    /*Shows all salaries of people in the passed in department.*/
    public static void showSalaries(Connection myConn, String dep) throws SQLException{
        boolean found = false;

        /*Create sql statement.  ? sign will be replaced by a variable.  In this case, replaced by
        * the dep var that is passed in.*/
        PreparedStatement myStmt = myConn.prepareStatement("select * from employees where department=?");

        /*Replace the ? place holder and execute the sql storing all results in the resultSet.*/
        myStmt.setString(1, dep);
        ResultSet results = myStmt.executeQuery();

        /*Print out results*/
        while(results.next()){
            System.out.println(results.getString("last_name") + ", " + results.getString("first_name") + " - " +
                    results.getString("department") + "($" + results.getInt("salary") + ")");

            found = true;
        }

        if(!found)
            System.out.println("No results found");
    }
}

