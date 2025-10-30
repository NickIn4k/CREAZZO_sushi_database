import java.sql.*;

public class DataBase {
    private final Connection connection;

    //In questo caso è meglio propagare => evito la creazione di un oggetto errato
    public DataBase() throws SQLException {
        String URL = "jdbc:sqlite:database/sushi.db";   //Quale file userai? è un path!
        connection = DriverManager.getConnection(URL);  //Driver: parte software per gestire funzioni hardware
        System.out.println("Connected to database successfully");
    }

    // CREATE
    public boolean insert(String nomePiatto, float prz, int qt) {
        if(!checkConnection())
            return false;

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

    // READ
    public String selectAll(){
        String rslt = "";
        if(!checkConnection())
            return null;

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

    public String selectByName(String nome){
        String msg = "";
        String query = "SELECT * FROM menu WHERE piatto = ?";

        if (!checkConnection())
            return null;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                msg += rs.getString("id") + "\t";
                msg += rs.getString("piatto") + "\t";
                msg += rs.getString("prezzo") + "\t";
                msg += rs.getString("quantita") + "\n";
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return null;
        }

        return msg;
    }

    public String selectByIndex(int key){
        String msg = "";
        String query = "SELECT * FROM menu WHERE id = ?";

        if (!checkConnection())
            return null;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                msg += rs.getString("id") + "\t";
                msg += rs.getString("piatto") + "\t";
                msg += rs.getString("prezzo") + "\t";
                msg += rs.getString("quantita") + "\n";
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return null;
        }

        return msg;
    }

    // DELETE
    public boolean deleteByName(String piatto){
        String query = "DELETE FROM menu WHERE piatto = ?";

        if(!checkConnection())
            return false;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteByIndex(int key){
        String query = "DELETE FROM menu WHERE id = ?";

        if(!checkConnection())
            return false;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return false;
        }

        return true;
    }

    // Metodo di check (per evitare codice ripetuto)
    private boolean checkConnection() {
        try {
            if(connection == null || !connection.isValid(5)){
                System.err.println("Errore di connessione al database");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            return false;
        }
        return true;
    }

}