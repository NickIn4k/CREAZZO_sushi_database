import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataBase db = null;
        try {
            db = new DataBase();
        } catch (SQLException e) {
            System.err.println("Errore di connessione: " + e.getMessage()); // Scrittura nello stream di errore
            System.exit(-1);
        }

        System.out.println("Inserire nome piatto");
        String nome = sc.nextLine();
        System.out.println("Inserire prezzo");
        sc.nextLine();
        float prz = sc.nextFloat();
        System.out.println("Inserire quantita");
        int qt = sc.nextInt();

        if(db.insert(nome, prz, qt))
            System.out.println("piatto inserito con successo");

        System.out.println(db.selectAll());
    }
}