package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.GiroKonto;
import at.htl.bank.model.SparKonto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {

  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";

  static List<BankKonto> konten = new ArrayList<>();

  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */


  public static void main(String[] args) {
    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);
  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   * <p>
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {

    try (Scanner scanner = new Scanner(new FileReader(KONTENDATEI))) {
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        String line = scanner.next();
        String[] array = line.split(";");
        String kontotyp = array[0];
        String name = array[1];
        double anfangsBetrag = Double.parseDouble(array[2]);

        switch (kontotyp) {
          case "Sparkonto":
            SparKonto sparKonto = new SparKonto(name, anfangsBetrag, ZINSSATZ);
            konten.add(sparKonto);
            break;
          case "Girokonto":
            GiroKonto giroKonto = new GiroKonto(name, anfangsBetrag, ZINSSATZ);
            konten.add(giroKonto);
            break;
        }
      }
      System.out.println("Erstellung der Konten beendet");

    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }

    /**
     * Die einzelnen Buchungen werden aus der Datei eingelesen.
     * Es wird aus der Liste "konten" jeweils das Bankkonto für
     * kontoVon und kontoNach gesucht.
     * Anschließend wird der Betrag vom kontoVon abgebucht und
     * der Betrag auf das kontoNach eingezahlt
     *
     * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
     * Buchung der Beträge beendet
     *
     * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
     *
     * @param datei BUCHUNGSDATEI
     */
    private static void fuehreBuchungenDurch (String datei){

      try (Scanner scanner = new Scanner(new FileReader(BUCHUNGSDATEI))) {
        scanner.nextLine();
        while (scanner.hasNextLine()){
          String line = scanner.nextLine();
          String[] array = line.split(";");
          String vonKonto = array[0];
          String aufKonto = array[1];
          double betrag = Double.parseDouble(array[2]);


          BankKonto bankKonto = findeKontoPerName(vonKonto);
          bankKonto.abheben(betrag);

          BankKonto bankKonto1 = findeKontoPerName(aufKonto);
          bankKonto1.einzahlen(betrag);
        }
        System.out.println("Buchung der Beträge beendet");
      }

      catch (FileNotFoundException e){
        System.err.println(e.getMessage());
      }
    }

    /**
     * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
     * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
     * gutgeschrieben
     *
     * Die Datei sieht so aus:
     *
     * name;kontotyp;kontostand
     * Susi;SparKonto;875.5
     * Mimi;GiroKonto;949.96
     * Hans;GiroKonto;1199.96
     *
     * Vergessen Sie nicht die Überschriftenzeile
     *
     * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
     * Ausgabe in Ergebnisdatei beendet
     *
     * @param datei ERGEBNISDATEI
     */
    private static void schreibeKontostandInDatei (String datei){

      try (PrintWriter writer = new PrintWriter(new FileWriter(ERGEBNISDATEI))) {
        writer.println("kontotyp;name:endbetrag");
        for (int i = 0; i < konten.size(); i++) {
          if (konten.get(i) instanceof SparKonto) {
            writer.println("Sparkonto;" + konten.get(i).getName() + ";" + konten.get(i).getKontoStand());
          }else {
            writer.println("Girokonto;" + konten.get(i).getName() + ";" + konten.get(i).getKontoStand());
            }
          }
        System.out.println("Ausgabe in Ergebnisdatei beendet");
      }
      catch (IOException e){
        System.err.println(e.getMessage());
      }
    }

    /**
     * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
     * übergebenen Namen
     * @param name
     * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
     *         nicht gefunden wird
     */
    public static BankKonto findeKontoPerName (String name){
      for (int i = 0; i < konten.size(); i++) {
        if (konten.get(i).getName().equals(name)) {
          return konten.get(i);
        }
      }
      return null;
    }
  }

