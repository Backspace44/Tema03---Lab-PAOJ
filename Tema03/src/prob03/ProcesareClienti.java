import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class ProcesareClienti {
    public static void main(String[] args) {
        List<Client> clienti = Arrays.asList(
                new Client("Ana", 30, 5000.0, Optional.of("VIP")),
                new Client("Bogdan", 25, 3000.0, Optional.of("Standard")),
                new Client("Cristina", 22, 1500.0, Optional.empty()),
                // ... restul clienților
                new Client("Mihai", 45, 9000.0, Optional.of("VIP"))
        );

        // Operatii cu stream-uri
        double mediaSuma = clienti.stream().mapToDouble(Client::getSumaCont).average().orElse(0.0);

        Predicate<Client> esteVIPPesteMedie = client ->
                client.getTipClient().orElse("").equals("VIP") && client.getSumaCont() > mediaSuma;

        Function<Client, String> formatNumeVarsta =
                client -> client.getNume() + " - " + client.getVarsta() + " ani";

        // Executare si afisare rezultate
        afiseazaRezultate(clienti, mediaSuma, esteVIPPesteMedie, formatNumeVarsta);
    }

    private static void afiseazaRezultate(List<Client> clienti, double mediaSuma,
                                          Predicate<Client> predicate, Function<Client, String> formatter) {
        System.out.println("=== Clienți VIP peste medie (" + mediaSuma + ") ===");
        clienti.stream().filter(predicate).forEach(c -> System.out.println(c.getNume()));

        System.out.println("\n=== Format Nume-Varsta ===");
        clienti.stream().map(formatter).forEach(System.out::println);

        // Restul logicii de afișare...
    }
}