package at.htl.bank.model;

public class GiroKonto extends BankKonto {

    private double gebuehr = 0.02;

    public GiroKonto(String name, double anfangsBestand, double gebuehr) {
        super(name, anfangsBestand);
        this.gebuehr = gebuehr;
    }

    public GiroKonto(String name, double gebuehr) {
        super(name);
        this.gebuehr = gebuehr;
    }

    @Override
    public void abheben(double betrag){
        kontoStand = kontoStand - betrag;
        kontoStand = kontoStand - gebuehr;
    }

    @Override
    public void einzahlen(double betrag){
        kontoStand = kontoStand - gebuehr;
        kontoStand = kontoStand + betrag;
    }
}
