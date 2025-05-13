import java.io.*;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String DATA_FILE = "produse.dat";
    private static final String ERROR_LOG  = "erori.log";
    private static final String OUT_OF_STOCK_FILE = "epuizate.txt";

    public static void main(String[] args) {
        // 1. Scriere produse
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for (int i = 1; i <= 10; i++) {
                // exemple de date; în realitate le-ai putea citi de la tastatură
                String name = "Produs" + i;
                double price = 10.0 * i;
                int stock = (i % 3 == 0) ? 0 : 50;
                Produs p = new Produs(name, price, stock);
                oos.writeObject(p);
            }
        } catch (IOException | InvalidDataException e) {
            // log în fișier
            try (PrintWriter pw = new PrintWriter(new FileWriter(ERROR_LOG, true))) {
                pw.println(new Date() + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        // 2. Citire produse
        List<Produs> produse = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while (true) {
                try {
                    Produs p = (Produs) ois.readObject();
                    produse.add(p);
                } catch (EOFException eof) {
                    break;  // sfârșit fișier
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 3. Flux și operații
        Stream<Produs> flux = produse.stream();

        // 3.a. produse epuizate → epuizate.txt
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUT_OF_STOCK_FILE))) {
            flux.filter(p -> p.getStoc() == 0)
                    .forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trebuie să recreăm fluxul, deoarece a fost terminal
        flux = produse.stream();

        // 3.b. reducem stoc cu 10%
        UnaryOperator<Produs> reducereStoc10 = p -> {
            try {
                int nou = (int) Math.floor(p.getStoc() * 0.9);
                p.setStoc(nou);
            } catch (InvalidDataException e) {
                // nu se întâmplă: înmulțire cu 0.9 nu produce negativ
            }
            return p;
        };
        List<Produs> reduced = flux.map(reducereStoc10).collect(Collectors.toList());

        // 3.c. produsul cu cel mai mare preț
        reduced.stream()
                .max(Comparator.comparingDouble(Produs::getPret))
                .ifPresent(p -> System.out.println("Cel mai scump: " + p));
    }
}
