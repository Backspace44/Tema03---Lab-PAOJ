import java.io.*;
import java.nio.channels.Channels;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String FILENAME = "comenzi.dat";
    private static final double THRESHOLD = 5000.0;

    public static void main(String[] args) {
        // 1) Scriem 15 comenzi inițiale
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            for (int i = 1; i <= 15; i++) {
                // Simulăm câteva comenzi
                int    id        = i;
                String client    = "Client" + ((i % 5) + 1);
                double valoare   = 1000.0 * i;      // 1000, 2000, …, 15000
                boolean finaliz = false;
                Comanda c = new Comanda(id, client, valoare, finaliz);
                oos.writeObject(c);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        // 2) Citim tot în List<Comanda>
        List<Comanda> lista = new ArrayList<>();
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILENAME))) {
            while (true) {
                try {
                    Comanda c = (Comanda) ois.readObject();
                    lista.add(c);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // 3) Marcăm drept finalizate comenzile peste prag
        lista.stream()
                .filter(c -> c.getValoare() > THRESHOLD)
                .forEach(c -> c.setFinalizata(true));

        // 4) Rescrivem fișierul cu RandomAccessFile
        try (RandomAccessFile raf = new RandomAccessFile(FILENAME, "rw")) {
            // golim conținutul
            raf.setLength(0);
            // wrap într-un ObjectOutputStream pe canalul RAF
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(Channels.newOutputStream(raf.getChannel()))) {
                for (Comanda c : lista) {
                    oos.writeObject(c);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        // 5) Citiți din nou și faceți stream-urile finale
        List<Comanda> updated = new ArrayList<>();
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILENAME))) {
            while (true) {
                try {
                    updated.add((Comanda) ois.readObject());
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // a) Filtrare comenzilor finalizate
        List<Comanda> finalizate =
                updated.stream()
                        .filter(Comanda::isFinalizata)
                        .collect(Collectors.toList());

        // b) Suma valorilor comenzilor finalizate
        double sumaFinalizate =
                finalizate.stream()
                        .mapToDouble(Comanda::getValoare)
                        .sum();

        // c) Grupare pe client
        Map<String,List<Comanda>> grupate =
                finalizate.stream()
                        .collect(Collectors.groupingBy(Comanda::getNumeClient));

        // --- Afisări de control ---
        System.out.println("=== Comenzi finalizate ===");
        finalizate.forEach(System.out::println);

        System.out.printf("Suma totală finalizate: %.2f RON%n", sumaFinalizate);

        System.out.println("=== Grupare pe client ===");
        grupate.forEach((client, comenzi) -> {
            System.out.println("Client: " + client);
            comenzi.forEach(c -> System.out.println("   " + c));
        });
    }
}
