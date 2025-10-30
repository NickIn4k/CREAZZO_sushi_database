# Sushi DB - Progetto Java CRUD con SQLite

## Descrizione

`Sushi DB` è un progetto Java che implementa un semplice database di piatti per un ristorante sushi utilizzando **SQLite**.  
Permette di effettuare le operazioni CRUD (Create, Read, Update, Delete) sui piatti memorizzati nel database.

Il progetto include:
- Classe `DataBase` per gestire le operazioni SQL con PreparedStatement.
- Classe `Main` con un **menu testuale interattivo** tramite console (switch-case gestito con funzioni esterne al main per maggiore leggibilità).

## Funzionalità

### Inserimento (Create)
- Aggiunge un nuovo piatto nel database con:
    - Nome (`String`)
    - Prezzo (`float`)
    - Quantità (`int`)

### Lettura (Read)
- Legge i dati dal database secondo tre modalità:
    - Tutti i record
    - Record filtrati per nome del piatto
    - Record filtrati per ID del piatto

### Modifica (Update)
- Modifica un record esistente individuandolo tramite **ID**.
- È possibile aggiornare:
    - Nome del piatto
    - Prezzo
    - Quantità

### Eliminazione (Delete)
- Elimina un piatto specificando:
    - **ID** oppure **nome del piatto**

## Struttura del database

La tabella principale `menu` ha le seguenti colonne:

| Colonna    | Tipo       | Note                 |
|------------|-----------|--------------------|
| id         | INTEGER   | Primary Key, auto-increment |
| piatto     | TEXT      | Nome del piatto     |
| prezzo     | REAL      | Prezzo              |
| quantita   | INTEGER   | Quantità disponibile |

## Migliorie per il progetto
- Creare più metodi di update diversi
- gestire meglio i return booleani dei metodi della classe DataBase
