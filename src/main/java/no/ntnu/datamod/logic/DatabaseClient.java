package no.ntnu.datamod.logic;
import no.ntnu.datamod.data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseClient {

    private String host = "192.168.50.50";
    private int port = 3306;
    private String database = "library_db";


    /**
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
     */
    public ArrayList<Book> getBooksList()  throws SQLException {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

            String fullCommand = "SELECT * FROM Books";
            ArrayList<Book> rowList = new ArrayList<>();
            Statement stm;

            // Create statement
            stm = connection.createStatement();

            // Query
            ResultSet result;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                throw new SQLException("There are no results from the given query \n");

            ArrayList<HashMap<String,Object>> rows = createObjectList(result);

            // Uses the previously created HashMap to match key for value
            // and creates the required object. And puts em all into a list.
            for (HashMap<String, Object> row : rows) {
                String publisher = (String) row.get("publisher");
                String title = (String) row.get("title");
                long idBook = (int) row.get("idBook");
                String authors = (String) row.get("authors");
                String isbn = (String) row.get("isbn");
                String image = (String) row.get("image");

                Book book = new Book(publisher, title, idBook, authors, isbn, image);
                rowList.add(book);
            }

            // Closes the statement
            stm.close();
            connection.close();
            return rowList;
    }

    /**
     * Returns all the users from the table Users as an ArrayList<User>.
     *
     * @return Returns all the users from the table Book as an ArrayList<User>.
     */
    public ArrayList<User> getUsersList() throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Users";
        ArrayList<User> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            long idUser = (int) row.get("idUser");
            String username = (String) row.get("username");
            String password = (String) row.get("password");
            String usertype = (String)row.get("usertype");
            User user = new User(idUser, username, password, usertype);
            rowList.add(user);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    /**
     * Returns all the branches from the table Branches as an ArrayList<Branch>.
     *
     * @return Returns all the branches from the table Book as an ArrayList<Branch>.
     */
    public ArrayList<Branch> getBranchList() throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Branches";
        ArrayList<Branch> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            long idBranch = (int) row.get("idBranch");
            String name = (String) row.get("name");
            String address = (String) row.get("address");
            Branch branch = new Branch(idBranch, name, address);
            rowList.add(branch);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    /**
     * Returns all the loans from the table Loans as an ArrayList<Loan>.
     *
     * @return Returns all the loans from the table Book as an ArrayList<Loan>.
     */
    public ArrayList<Loan> getLoansList() throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Loans";
        ArrayList<Loan> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            long idLoans = (int) row.get("idLoans");
            java.sql.Date loanDate = (java.sql.Date) row.get("loanDate");
            java.sql.Date loanDue = (java.sql.Date) row.get("loanDate");
            long idBook = (int) row.get("idBook");
            long idUser = (int) row.get("idUser");

            Loan loan = new Loan(idLoans, loanDate, loanDue, idBook, idUser);
            rowList.add(loan);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    /**
     * Creates an ArrayList with a HashMap that contains mappings
     * to each of the field's values.
     * @param result the ResultSet
     * @return Returns an ArrayList with HashMap that contains mappings
     *      to each of the field's values.
     * @throws SQLException todo
     */
    private ArrayList<HashMap<String,Object>> createObjectList(ResultSet result) throws SQLException {

        ArrayList<HashMap<String, Object>> rows = new ArrayList<>();

        // Get metadata
        ResultSetMetaData meta ;
        meta = result.getMetaData();

        // Get column names
        int colCount = meta.getColumnCount();
        ArrayList<String> cols = new ArrayList<>();
        for (int index=1; index <= colCount; index++)
            cols.add(meta.getColumnName(index));

        while (result.next()) {
            HashMap<String,Object> row = new HashMap<>();
            for (String colName:cols) {
                Object val = result.getObject(colName);
                row.put(colName,val);
            }
            rows.add(row);
        }

        return rows;
    }

    /**
     * Returns the quantity of a specific Book, in a specific Branch.
     *
     * @param bookID bookID
     * @param branchID branchID
     * @return Returns the quantity of a specific Book, in a specific Branch. Returns 0 if an Exception occurs.
     */
    public int getQuantity(long bookID, long branchID) {
        try {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();
            int size = 0;

            String fullCommand =
                    "SELECT quantity " +
                    "FROM Book_Quantity " +
                    "WHERE idBook = " + bookID + " AND idBranch = " + branchID + ";";

            Statement stm = connection.createStatement();

            // Query
            ResultSet result;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                throw new SQLException("There are no results from the given query \n");

            if(result.next()) {
                size = result.getInt(1);
            }
            connection.close();
            return size;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        }

        // Add rows to the database //

    /**
     * Adds a new user to the database with the given parameter values
     * defining the user to be added.
     *
     *
     *
     * @param password password
     * @param usertype the type of user
     * @param username username
     * @return Returns number of rows affected.
     */
    public int addUserToDatabase(String username, String password, String usertype) throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Users (username, password, usertype) VALUES" +
                    "( '" +
                    username + "', '" +
                    password + "', '" +
                    usertype + "' );";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param title Book title
     * @param publisher Book publisher
     * @param ISBN Book ISBN
     * @param image Book image???
     * @return Number of edited rows
     * @throws SQLException
     */
    public int addBookToDatabase(String title, String publisher, String ISBN, String image) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Books (title, publisher, ISBN, image) VALUES ('" +
                    title + "', '" +
                    publisher + "', '" +
                    ISBN + "', '" +
                    image + "');";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param title Book title
     * @param publisher Book publisher
     * @return Number of edited rows
     * @throws SQLException
     */
    public int addBookToDatabase(String title, String publisher) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Books (title, publisher, ISBN, image) VALUES ('" +
                    title + "', '" +
                    publisher + "', " +
                    null + ", " +
                    null + ");";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idBook The book ID of the book that is borrowed.
     * @param idUser The borrowers user ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addLoanToDatabase(long idBook, long idUser) throws SQLException{
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

            try {
                String loanDate = "CURDATE()";
                String loanDue = "DATE_ADD(CURDATE(), INTERVAL 2 MONTH)";

                String fullCommand = "INSERT INTO Loans (loanDate, loanDue, idBook, idUser) VALUES (" +
                        loanDate + ", " +
                        loanDue + ", " +
                        idBook + ", " +
                        idUser + ");";



                // Create statement
                Statement stm = null;
                stm = connection.createStatement();

                // Query
                int execution = stm.executeUpdate(fullCommand);
                connection.close();
                return execution;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                connection.close();
                return 0;
            }
    }

    /**
     *
     * @param fName Firstname of author.
     * @param lName Lastname of author
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addAuthorToDatabase(String fName, String lName) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Authors (lName, fName) VALUES ('" +
                    lName + "', '" +
                    fName + "');";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param name Name of the branch.
     * @param address The branches address.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBranchToDatabase(String name, String address) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Branches (name, address) VALUES ('" +
                    name + "', '" +
                    address + "');";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param fname Firstname of the customer.
     * @param lname Lastname of the customer.
     * @param address Customers address.
     * @param phone Customers phone number
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addCustomerToDatabase(String fname, String lname, String address, String phone) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Customer (fname, lname, address, phone) VALUES ('" +
                    fname + "', '" +
                    lname + "', '" +
                    address + "', '" +
                    phone + "');";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param fname Firstname of the employee.
     * @param lname Lastname of the employee.
     * @param address Employees address.
     * @param phone Employees phone number.
     * @param accountNumber Employees accountnumber.
     * @param SSN Employees social security number.
     * @param position Employees working position.
     * @param idBranch Branch ID of where the employee working at.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addEmployeeToDatabase(String fname, String lname, String address, String phone, String accountNumber, int SSN, String position, float idBranch) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Employee (fname, lname, address, phone, accountNumber, SSN, position, idBranch) VALUES ('" +
                    fname + "', '" +
                    lname + "', '" +
                    address + "', '" +
                    phone + "', '" +
                    accountNumber + "', " +
                    SSN + ", '" +
                    position + "', " +
                    idBranch + ");";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param name The genre name.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addGenreToDatabase(String name) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Genre (name) VALUES ('" +
                    name + "');";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idBook Book ID.
     * @param idAuthor Author ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBookAuthorJunctionToDatabase(long idBook, long idAuthor) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Book_Authors (idBook, idAuthors) VALUES (" +
                    idBook + ", " +
                    idAuthor + ");";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idBook Book ID.
     * @param idGenre Genre ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBookGenreJunctionToDatabase(long idBook, long idGenre) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Book_Genres (idBook, idGenre) VALUES (" +
                    idBook + ", " +
                    idGenre + ");";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idUser User ID.
     * @param idCustomer Customer ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addCustomerUserJunctionToDatabase(long idUser, long idCustomer) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {



            String fullCommand = "INSERT INTO Customer_Users (Users_idUser, Customer_idCustomer) VALUES (" +
                    idUser + ", " +
                    idCustomer + ");";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idUser User ID.
     * @param idEmployee Employee ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addEmployeeUserJunctionToDatabase(long idUser, long idEmployee) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {



            String fullCommand = "INSERT INTO Employee_Users (Users_idUser, Employee_idEmployee) VALUES (" +
                    idUser + ", " +
                    idEmployee + ");";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    // Remove rows from database // TODO: We might not delete rows at all. So we could probably delete the remove*FomDatabase() methods.

    public int removeBookFromDatabase(long idBook) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "DELETE FROM Books WHERE idBook = " + idBook + ";";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }


    /**
     * Searches for all books that match the given keyword.
     *
     * @param keyword The given keyword to search with
     */
    public Book findBookMatch(String keyword) {
        /* todo
        Create a method that finds a book with a keyword
        and returns it as a Book.
         */
        return null;
    }

    /**
     * Removes one book copy in the Book_Quantity table
     *
     * @return Returns back the shoppingCart with all the remaining books that couldn't be borrowed.
     * If it returns an empty HashMap all went well.
     */
    public HashMap<Literature, Branch> updateQuantity(HashMap<Literature, Branch> shoppingCart) {
        try {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();
            Statement stm = connection.createStatement();

             for (HashMap.Entry<Literature, Branch> entry : shoppingCart.entrySet()) {
                 Book book = (Book) entry.getKey();
                 Branch branch = entry.getValue();
                 long idBook = book.getIdBook();
                 long idBranch = branch.getIdBranch();

                 if (getQuantity(idBook, idBranch) >= 1) {
                     String fullCommand =
                             "UPDATE Book_Quantity " +
                                     "SET quantity = quantity - 1 " +
                                     "WHERE idBook = " + idBook + " AND idBranch = " + idBranch + ";";
                     stm.execute(fullCommand);
                     shoppingCart.remove(entry, branch);
                 }
             }

             stm.close();
             connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return shoppingCart;
        }
        return shoppingCart;
    }

    /**
     * Tries to login to the application checking the database's users table for
     * a match on username and password.
     *
     * @param username username
     * @param password password
     * @return
     */
    public boolean tryLogin(String username, String password) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();
        Statement stm = connection.createStatement();

        boolean result = false;

        String fullCommand =
                        "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "';";

        // Query
        stm.execute(fullCommand);
        ResultSet queryResult = stm.getResultSet();
        if (queryResult.next()) {
            result = true;
        }

        stm.close();
        connector.closeConnection();

        return result;
    }
}