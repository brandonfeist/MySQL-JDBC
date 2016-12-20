/**
 * Sajay Shah, Mohnish Kadakia, Brandon Feist, Rahul Patel, Harkanwar Singh
 *
 * Books, main project class that executes methods to create, populate, and manipulate the database.
 */

import java.sql.*;

public class Books {

    // The Driver and the Database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    
    public static void main(String[] args) {
    	SqlAccess sqlAccess = null;
    	SqlAccess sqlAccessBook = null;
    	
        try{
        	// Init MySQL and JDBC Drivers
            Class.forName(JDBC_DRIVER);

            // Open connection to Database and Books Database.
            System.out.println("Connecting to database...");
            sqlAccess = new SqlAccess(DB_URL, USER, PASS);
            sqlAccessBook = new SqlAccess("jdbc:mysql://localhost:3306/books" , USER , PASS);
            
            // Check if books exist, drop existing database, then create new books database.
            System.out.println("Creating database...");
            if (sqlAccess.getQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'books'").next()) {
            	sqlAccess.setQuery("DROP DATABASE books");
            }

            String booksQuery = "CREATE DATABASE books";
            sqlAccess.createTable(booksQuery);
            System.out.println("Database created successfully...");

            // Create and initialize the tables with random values
            TableTool.createTables(sqlAccessBook);
            TableTool.autoPopulate(sqlAccessBook);
            System.out.println();
            System.out.println();
            
            /*
             * Project Queries
             */
            
            // Select all authors from the authors table. Order the information
            // alphabetically by the author’s last name and first name.

            ResultSet selectAuthorsResults = sqlAccess.getQuery("SELECT * FROM Books.authors ORDER BY last, first");

            System.out.println("Selecting authors from the author's table. Ordering author's by last and first name: ");
            System.out.println("The records selected are:");
            System.out.println();

            System.out.println("AuthorID, Last, First: ");
            while(selectAuthorsResults.next()) {   // Move the cursor to the next row
                int id = selectAuthorsResults.getInt("authorID");
                String first = selectAuthorsResults.getString("first");
                String last = selectAuthorsResults.getString("last");
                System.out.println(id + ", " + last + ", " + first);
            }
            System.out.println();
            System.out.println();


            // Select all publishers from the publishers table

            ResultSet selectPublishersResults = sqlAccess.getQuery("SELECT * FROM Books.publishers");

            System.out.println("Select the publishers from the publisher table: ");
            System.out.println("The records selected are:");
            System.out.println();

            System.out.println("publisherID, publisherName: ");
            while(selectPublishersResults.next()) {   // Move the cursor to the next row
                int id = selectPublishersResults.getInt("publisherID");
                String publisherName = selectPublishersResults.getString("publisherName");
                System.out.println(id + ", " + publisherName);
            }
            System.out.println();
            System.out.println();


            // Select a specific publisher and list all books published by that
            // publisher. Include the title, year and ISBN number. Order the information alphabetically by title.

            System.out.println("Select a specific publisher and list all books published by that\n" +
                    "publisher. Include the title, year and ISBN number. Order the information alphabetically by title");
            System.out.println("The records selected are:");
            System.out.println();
            
            System.out.println("title, year, ISBN Number: ");
                ResultSet publisherBooksResults = sqlAccess.getQuery("SELECT isbn, title, year FROM books.titles "
                		+ "WHERE publisherID = 1 ORDER BY title");
                while(publisherBooksResults.next()){
                    System.out.println(publisherBooksResults.getString("title") + " , " +  publisherBooksResults.getString("year") + " , " +  publisherBooksResults.getString("isbn"));
                }

            System.out.println();
            System.out.println();

            // Add new author
            sqlAccess.setQuery("INSERT INTO books.authors (first, last) VALUES ('Mohnish', 'Kadakia');");
            // Print out author table with the recently added Author
            System.out.println("Add new author: Mohnish, Kadakia");
            System.out.println("The records selected are:");
            System.out.println();

            ResultSet authorResults = sqlAccess.getQuery("SELECT * FROM Books.authors");

            System.out.println("Author table after Mohnish, Kadakia is added: ");
            System.out.println("The updated records are:");
            System.out.println();
            System.out.println("AuthorID, First, Last");
            while(authorResults.next()) {   // Move the cursor to the next row
                int id = authorResults.getInt("authorID");
                String first = authorResults.getString("first");
                String last = authorResults.getString("last");
                System.out.println(id + ", " + first + ", " + last);
            }
            System.out.println();
            System.out.println();


            // Edit/Update the existing information about an author
            sqlAccess.setQuery("UPDATE books.authors SET first = \"Bob\" WHERE authorID = 1");

            // Print out Author table with updated value
            ResultSet authorResults2 = sqlAccess.getQuery("SELECT * FROM Books.authors");

            System.out.println("Edit/Update the existing information about an author: ");
            System.out.println("The updated records are: Changed authorID = 1 firstName to Bob");
            System.out.println();
            System.out.println("AuthorID, First, Last");
            while(authorResults2.next()) {   // Move the cursor to the next row
                int id = authorResults2.getInt("authorID");
                String first = authorResults2.getString("first");
                String last = authorResults2.getString("last");
                System.out.println(id + ", " + first + ", " + last);
            }
            System.out.println();
            System.out.println();


            // Add a new title for an author
            String isbn = "'1234567890'";
            sqlAccess.setQuery("INSERT INTO books.titles (isbn, title, editionNumber, price, year, publisherID)" +
                    " VALUES (" + isbn + ", 'TestBook', 1234, 25.00, 1991, 1);");
            sqlAccess.setQuery("INSERT INTO books.authorISBN (authorID, isbn) " +
                    "VALUES (1, " + isbn + ")");

            System.out.println("Add a new title for an author: ");
            System.out.println("The updated records are:");
            System.out.println();
            System.out.println("The new title \"TestBook\" is now added to authorId = 1");


            // Add new publisher
            sqlAccess.setQuery("INSERT INTO books.publishers (publisherName) VALUES ('TestPublisher');");
            // Print out the publishers Table with the new publisher added.
            ResultSet publisherResults = sqlAccess.getQuery("SELECT * FROM Books.publishers");

            System.out.println("Add a new publisher: TestPublisher");
            System.out.println("The updated records are:");
            System.out.println();


            System.out.println("publisherID, publisherName");
            while(publisherResults.next()) {   // Move the cursor to the next row
                int id = publisherResults.getInt("publisherID");
                String publisherName = publisherResults.getString("publisherName");
                System.out.println(id + ", " + publisherName);
            }
            System.out.println();
            System.out.println();


            // Edit/Update the existing information about a publisher
            sqlAccess.setQuery("UPDATE books.publishers SET publisherName = \"NewName\" WHERE publisherID = 1;");
            System.out.println("Edit/Update the existing information about a publisher: ");
            System.out.println("The updated records are: Change publisherID = 1 name to \"NewName\"");
            System.out.println();
            // Print out the publishers table with the updated value
            ResultSet publisherResults2 = sqlAccess.getQuery("SELECT * FROM Books.publishers");

            System.out.println("publisherID, publisherName");
            while(publisherResults2.next()) {   // Move the cursor to the next row
                int id = publisherResults2.getInt("publisherID");
                String publisherName = publisherResults2.getString("publisherName");
                System.out.println(id + ", " + publisherName);
            }



        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(sqlAccess != null) {
			    sqlAccess.close();
			    sqlAccessBook.close();
            }
        }
        
        System.out.println();
        System.out.println();
        System.out.println("System END");
    }
}

