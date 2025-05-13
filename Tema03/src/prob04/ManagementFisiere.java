import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ManagementFisiere {
    public static void main(String[] args) {
        List<Persoana> persoane = new ArrayList<>();

        // Citire date
        try (BufferedReader br = new BufferedReader(new FileReader("date.txt"))) {
            persoane = br.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        return new Persoana(
                                parts[0].trim(),
                                Integer.parseInt(parts[1].trim()),
                                parts[2].trim()
                        );
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Eroare la citire: " + e.getMessage());
            return;
        }

        // Procesare date
        List<Persoana> filtrate = persoane.stream()
                .filter(p -> p.getVarsta() > 30 && p.getOras().startsWith("B"))
                .sorted(Comparator.comparing(Persoana::getNume).thenComparingInt(Persoana::getVarsta))
                .collect(Collectors.toList());

        Map<String, Double> mediiOrase = persoane.stream()
                .collect(Collectors.groupingBy(
                        Persoana::getOras,
                        Collectors.averagingDouble(Persoana::getVarsta)
                ));

        Optional<Persoana> varstaMaxima = persoane.stream()
                .max(Comparator.comparingInt(Persoana::getVarsta));

        // Scriere rezultate
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rezultat.txt"))) {
            bw.write("=== Persoane filtrate și sortate ===\n");
            filtrate.forEach(p -> {
                try { bw.write(p.toString() + "\n"); }
                catch (IOException e) { e.printStackTrace(); }
            });

            bw.write("\n=== Medii de vârstă pe orașe ===\n");
            mediiOrase.forEach((oras, medie) -> {
                try {
                    bw.write(String.format("%s: %.2f ani\n", oras, medie));
                } catch (IOException e) { e.printStackTrace(); }
            });

            bw.write("\n=== Persoana cu vârsta maximă ===\n");
            varstaMaxima.ifPresent(p -> {
                try { bw.write(p.toString() + "\n"); }
                catch (IOException e) { e.printStackTrace(); }
            });

        } catch (IOException e) {
            System.err.println("Eroare la scriere: " + e.getMessage());
        }
    }
}