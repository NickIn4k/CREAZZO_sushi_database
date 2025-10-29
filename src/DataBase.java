import java.sql.*;

public class DataBase {
    private Connection connection;

    //In questo caso è meglio propagare => evito la creazione di un oggetto errato
    public DataBase() throws SQLException {
        String URL = "jdbc:sqlite:database/sushi.db";   //Quale file userai? è un path!
        connection = DriverManager.getConnection(URL);  //Driver: parte software per gestire funzioni hardware
        System.out.println("Connected to database successfully");
    }

    public String selectAll(){
        String rslt = "";
        try {
            if(connection == null || !connection.isValid(5)){
                System.err.println("Errore di connessione al database");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
            return null;
        }

        String query = "SELECT * FROM menu";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                rslt += rs.getString("id") + "\t";
                rslt += rs.getString("piatto") + "\t";
                rslt += rs.getString("prezzo") + "\t";
                rslt += rs.getString("quantita") + "\n";
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
            return null;
        }

        return rslt;
    }

    public boolean insert(String nomePiatto, float prz, int qt) {
        try {
            if(connection == null || !connection.isValid(5)){
                System.err.println("Errore di connessione al database");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
            return false;
        }

        String query = "INSERT INTO menu(piatto, prezzo, quantita) VALUES (?, ?, ?)"; //i ? servono per evitare le SQL injections
        System.out.println(query);
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nomePiatto);
            statement.setFloat(2, prz);
            statement.setInt(3, qt);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());
            return false;
        }

        return true;
    }
}