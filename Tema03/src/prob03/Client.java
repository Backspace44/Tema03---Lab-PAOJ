import java.util.Optional;

public class Client {
    private String nume;
    private int varsta;
    private double sumaCont;
    private Optional<String> tipClient;

    public Client(String nume, int varsta, double sumaCont, Optional<String> tipClient) {
        this.nume = nume;
        this.varsta = varsta;
        this.sumaCont = sumaCont;
        this.tipClient = tipClient;
    }

    // Getters
    public String getNume() { return nume; }
    public int getVarsta() { return varsta; }
    public double getSumaCont() { return sumaCont; }
    public Optional<String> getTipClient() { return tipClient; }
}