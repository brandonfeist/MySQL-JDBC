import java.util.Random;

/**
 * Sajay Shah, Mohnish Kadakia, Brandon Feist, Rahul Patel, Harkanwar Singh
 *
 * TableTool contains static methods to quickly make the required tables and then populate them.
 */
public class TableTool {

    /**
     * A static function that creates author, authorISBN, publisher, and titles table
     * using given SqlAccess object.
     * 
     * @param sqlAccess SqlAccess object to create tables.
     */
    public static void createTables(SqlAccess sqlAccess){


        String authorTable="CREATE TABLE authors "
                + "(authorID INTEGER not NULL auto_increment, "
                + " first VARCHAR(255), "
                + " last VARCHAR(255), "
                + " PRIMARY KEY ( authorID ))";

        String authorISBNTable="CREATE TABLE authorISBN "
                + "(authorID INT, "
                + "isbn CHAR(10), "
                + "FOREIGN KEY ( authorID )REFERENCES authors( authorID ), "
                + "FOREIGN KEY ( isbn )REFERENCES titles( isbn ))";

        String publisherTable="CREATE TABLE publishers "
                + "(publisherID INTEGER not NULL auto_increment, "
                + " publisherName char(100), "
                + " PRIMARY KEY ( publisherID))";

        String titlesTable="CREATE TABLE titles "
                + "(isbn CHAR(10), "
                + " title VARCHAR(500), "
                + " editionNumber INTEGER, "
                + " year INTEGER, "
                + " publisherID INTEGER, "
                + " price FLOAT, "
                + " PRIMARY KEY ( isbn ), "
                + " FOREIGN KEY ( publisherID )REFERENCES publishers( publisherID ))";
        try{
            // Executing query to add the tables
        	sqlAccess.createTable(authorTable);
        	sqlAccess.createTable(publisherTable);
        	sqlAccess.createTable(titlesTable);
        	sqlAccess.createTable(authorISBNTable);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static function that auto-populates the tables created in createTables function.
     * 
     * @param sqlAccess SqlAccess object used to update the created tables.
     */
    public static void autoPopulate(SqlAccess sqlAccess)
    {
        try {
        	//Create Arrays of Random Words
             String[] firstName = BookDBDefaultData.getDataFromFile("firstNames.txt");
             String[] lastName = BookDBDefaultData.getDataFromFile("lastNames.txt");
             String[] bookTitle =  BookDBDefaultData.getDataFromFile("book_title.txt");
             String[] publishers =  BookDBDefaultData.getDataFromFile("publishers.txt");
             String[] bookEditionNum =  BookDBDefaultData.getDataFromFile("bookEditionNumber.txt");
             String[] bookPrice = BookDBDefaultData.getDataFromFile("bookPrice.txt");
             String[] bookYears =  BookDBDefaultData.getDataFromFile("bookYears.txt");
             String[] isbn =  BookDBDefaultData.getDataFromFile("isbn.txt");


            
            // Loop through 20 times and Auto fill all table values
            for (int i = 0; i < 20; i++)
            {
            	int id = i + 1;
            	
                String inAuthor = "INSERT INTO books.authors (authorID, first, last) "
                		+ "VALUES (" + id + ", '" + firstName[i] +"', '" + lastName[i] + "');";
                
                sqlAccess.setQuery(inAuthor);

                String inPublisher = "INSERT INTO Books.publishers (publisherID, publisherName) "
                		+ "VALUES (" + id + ", '" + publishers[i] + "');";

                sqlAccess.setQuery(inPublisher);
            }
            
            for(int i = 0; i < 20; i++) {
            	int id = i + 1;
            	Random random = new Random();
            	int randomInt = 0;
            	if(i % 2 == 0) {
            		randomInt = (random.nextInt((20 - 1) + 1) + 1);
            	} else {
            		randomInt = 1;
            	}
            	
            	String inTitles = "INSERT INTO Books.titles (isbn, title, editionNumber, year, publisherID, price) "
                		+ "VALUES ('" + isbn[i] + "', " + "'" + bookTitle[i] + "', " + bookEditionNum[i] + ", " + bookYears[i] + ", " + randomInt + ", " + bookPrice[i] + ");";

                sqlAccess.setQuery(inTitles);

                String inISBN = "INSERT INTO Books.authorISBN (authorID, isbn) "
                		+ "VALUES (" + id + ", '" + isbn[i] + "');";

                sqlAccess.setQuery(inISBN);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
