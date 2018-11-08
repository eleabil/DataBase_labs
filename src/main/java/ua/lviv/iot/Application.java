package ua.lviv.iot;

import java.sql.*;
import java.util.Scanner;

public class Application {
    private static final String url =
            "jdbc:mysql://localhost:3306/music?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "41653";

    private static Connection connection=null;
    private static Statement statement=null;
    private static ResultSet rs=null;

    public static void main(String args[]){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            connection = DriverManager.getConnection(url, user, password);


            statement=connection.createStatement();


            readData();

 //       updateDataAlbum();
  //       readData();
  //      insertDataAlbum();

 //        deleteDataAlbum();

        CallProcedureForInsertSingerAlbum();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try { rs.close(); } catch (SQLException e) { } // ignore
            if (statement != null) try { statement.close(); } catch (SQLException e) { }
            if (connection != null) try { connection.close(); } catch (SQLException e) { }
        }
    }

    private static void readData() throws SQLException {

        rs=statement.executeQuery("SELECT * FROM album");

        while (rs.next()) {
            String name = rs.getString("name_album");
            System.out.format("\ncount: %s\n", name);
        }

        rs=statement.executeQuery("SELECT * FROM album");

        // 4. Process the result set
        System.out.format("\n------------------------------ Table Album -------------------------------------\n");
        System.out.format("|%-3s| %-20s| %-20s| %-15s| %-12s|\n", "ID", "name", "genre", "label", "release_year");
        while (rs.next())
        {
            int id = rs.getInt("id_album");
            String name = rs.getString("name_album");
            String genre = rs.getString("genre");
            String label = rs.getString("label");
            String release_year = rs.getString("release_year");
            // Simply Print the results
            System.out.format("|%-3s| %-20s| %-20s| %-15s| %-12s|\n", id, name, genre, label, release_year);
        }
        //endregion

//region    SELECT * FROM Singer //
        // 3. executing SELECT query
        rs=statement.executeQuery("SELECT * FROM singer");

        // 4. Process the result set
        System.out.format("\n------ Table Singer ------\n");
        System.out.format("|%-3s| %-18s| \n", "ID", "name");
        while (rs.next())
        {
            int id=rs.getInt("id_singer");
            String name = rs.getString("name_singer");
            // Simply Print the results
            System.out.format("|%-3d| %-18s|\n", id, name);
        }
        //endregion

//region    SELECT * FROM relese_year //
        // 3. executing SELECT query
        rs=statement.executeQuery("SELECT * FROM release_year");

        // 4. Process the result set
        System.out.format("\n- Table Release year -\n");
        System.out.format("|%-12s|\n", "release year");
        while (rs.next())
        {
            String year = rs.getString("release_year");
            // Simply Print the results
            System.out.format("|%-12s|\n", year);
        }
        //endregion

//region    SELECT * FROM PersonBook //
        // 3. executing SELECT query
        String query="Select " +
                "(SELECT name_singer FROM singer WHERE id_singer=sa.id_singer) AS singer, " +
                "(SELECT name_album FROM album WHERE id_album=sa.id_album) AS album "+
                "FROM singer_album AS sa";
        rs=statement.executeQuery(query);

        // 4. Process the result set
        System.out.format("\nJoining Table singer_album --------------------\n");
        System.out.format("|%-15s| %s|\n", "singer", "album");
        while (rs.next())
        {
            String singer = rs.getString("singer");
            String album = rs.getString("album");
            // Simply Print the results
            System.out.format("|%-15s| %s|\n", singer, album);
        }
        //endregion

    }


 //-------------------------------------------------------------------------------------
    private static void updateDataAlbum() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input the name of album you want to update: ");
        String name_album = input.next();
        System.out.println("Input new name of album: "+ name_album);
        String name_album_new = input.next();

        // 3. executing SELECT query
// 1
       statement.execute("UPDATE album SET name_album='"+name_album_new+"' WHERE name_album='"+name_album+"';");

// 2  Returns count of updated rows
//       int n =statement.executeUpdate("UPDATE album SET name_album='"+name_album_new+"' WHERE name_album='"+name_album+"';");
//       System.out.println("Count rows that updated: "+n);

// 3  PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("UPDATE album SET name_album=? WHERE name_album=?;");
        preparedStatement.setString(1, name_album_new);
        preparedStatement.setString(2, name_album);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that updated: "+n);

    }

    private static void insertDataAlbum() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new singer: ");
        String new_singer = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("INSERT into singer (name_singer) VALUES (?)");
        preparedStatement.setString(1, new_singer);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: "+n);

    }

    private static void deleteDataAlbum() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name of album to delete: ");
        String album = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("DELETE FROM album WHERE name_album=?");
        preparedStatement.setString(1, album);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: "+n);
    }

    //----------------------------------------------------------------------------------------------------
    private static void CallProcedureForInsertSingerAlbum() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput singer's name: ");
        String singer = input.next();
        System.out.println("Input album: ");
        String album = input.next();

        CallableStatement callableStatement;
        callableStatement= connection.prepareCall("{call InsertSingerAlbum(?, ?)}");
        callableStatement.setString("NameSingerIn",singer);
        callableStatement.setString("AlbumNameIN",album);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next())
        {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: "+msg);
        }
    }

}
