package at.htl.bank.model;

public class SparKonto extends BankKonto {

    private double zinsSatz = 0.03;

    public SparKonto(String name, double anfangsBestand, double zinsSatz) {
        super(name, anfangsBestand);
        this.zinsSatz = zinsSatz;
    }

    public SparKonto(String name, double anfangsBestand) {
        super(name, anfangsBestand);
    }

    public void zinsenAnrechnen(){
        kontoStand = kontoStand + (kontoStand * zinsSatz);
    }
}
