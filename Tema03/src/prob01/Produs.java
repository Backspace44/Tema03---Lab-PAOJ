import java.io.Serializable;

public class Produs implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nume;
    private double pret;
    private int stoc;

    public Produs(String nume, double pret, int stoc) throws InvalidDataException {
        if (pret < 0) {
            throw new InvalidDataException("Preț negativ: " + pret);
        }
        if (stoc < 0) {
            throw new InvalidDataException("Stoc negativ: " + stoc);
        }
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) throws InvalidDataException {
        if (stoc < 0) {
            throw new InvalidDataException("Stoc negativ: " + stoc);
        }
        this.stoc = stoc;
    }

    @Override
    public String toString() {
        return String.format("Produs[nume=%s, pret=%.2f, stoc=%d]", nume, pret, stoc);
    }
}
