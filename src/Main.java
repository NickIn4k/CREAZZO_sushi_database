import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataBase db = null;
        String rsp;
        try {
            db = new DataBase();
        } catch (SQLException e) {
            System.err.println("Errore di connessione: " + e.getMessage()); // Scrittura nello stream di errore
            System.exit(-1);
        }


        // Menu di scelta operazioni CRUD

        System.out.println("====================== SUSHI DB ======================");
        do{
            System.out.println(
                "Benvenuto, scegli cosa fare:\n" +
                    "1. Inserisci un nuovo record \n" +
                    "2. Leggi i dati \n" +
                    "3. Modifica un record \n" +
                    "4. Elimina un record \n"
            );

            int nm = sc.nextInt();
            sc.nextLine();

            switch (nm){
                case 1:
                    insert(sc,db);
                    sc.nextLine(); // buffer
                    break;
                case 2:
                    leggi(sc,db);
                    break;
                case 3:
                    modifica(sc,db);
                    sc.nextLine(); // buffer
                    break;
                case 4:
                    delete(sc,db);
                    break;
            }
            System.out.println("Vuoi fare altro?\n ['si' per continuare]");
            rsp = sc.nextLine();
        }while(rsp.equalsIgnoreCase("si"));
    }

    static private void insert(Scanner sc, DataBase db){
        System.out.println("Inserire nome piatto");
        String nome = sc.nextLine();

        System.out.println("Inserire prezzo");
        float prz = sc.nextFloat();

        System.out.println("Inserire quantita");
        int qt = sc.nextInt();

        if(db.insert(nome, prz, qt))
            System.out.println("piatto inserito con successo");
    }

    static private void delete(Scanner sc, DataBase db) {
        System.out.println("Inserisci l'ID o il nome del piatto da eliminare:");

        // check next input => int
        if (sc.hasNextInt()) {
            int id = sc.nextInt();
            sc.nextLine(); // pulizia buffer

            if (db.deleteByIndex(id))
                System.out.println("Piatto eliminato correttamente (ID: " + id + ")");
            else
                System.err.println("Errore: nessun piatto trovato con ID " + id );
        } else {
            // oppure è una string
            String nome = sc.nextLine();

            if (db.deleteByName(nome))
                System.out.println("Piatto '" + nome + "' eliminato correttamente.");
            else
                System.err.println("Errore: nessun piatto trovato con nome '" + nome);
        }
    }

    static private void leggi(Scanner sc, DataBase db) {
        System.out.println("Come vuoi leggere i dati? \n" +
            "1. Tutti i record \n" +
            "2. Ricerca per nome \n" +
            "3. Ricerca per ID \n"
            );

        int scelta = sc.nextInt();
        sc.nextLine(); // pulizia buffer

        switch (scelta) {
            case 1:
                System.out.println(db.selectAll());
                break;
            case 2:
                System.out.println("Inserisci il nome del piatto:");
                String nome = sc.nextLine();

                System.out.println(db.selectByName(nome));
                break;
            case 3:
                System.out.println("Inserisci l'ID del piatto:");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.println(db.selectByIndex(id));
                break;
        }
    }

    static private void modifica(Scanner sc, DataBase db) {
        System.out.println("Inserisci l'ID del piatto da modificare:");
        int id = sc.nextInt();
        sc.nextLine(); // pulizia buffer

        // Verifica se l'ID esiste nel database
        String record = db.selectByIndex(id);
        if (record == null || record.isEmpty()) {
            System.err.println("Nessun piatto trovato con ID " + id);
            return;
        }

        System.out.println("Record trovato:");
        System.out.println(record);

        // Richiesta dei nuovi dati
        System.out.println("Inserisci il nuovo nome del piatto: ");
        String nome = sc.nextLine();

        System.out.println("Inserisci il nuovo prezzo: ");
        float prezzo = sc.nextFloat();
        sc.nextLine(); // pulizia buffer

        System.out.println("Inserisci la nuova quantità: ");
        int quantita = sc.nextInt();

        if(db.updateById(id, nome, prezzo, quantita))
            System.out.println("Piatto modificato correttamente (ID: " + id + ")");
    }
}