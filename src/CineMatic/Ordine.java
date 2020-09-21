/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CineMatic;

import db_Online.Biglietto;
import java.util.LinkedList;

/**
 *
 * @author david
 */
public class Ordine {
    int idOrdine;
    float prezzoTotale;
    
    LinkedList<Biglietto> listaBiglietti;
    
    public Ordine(){
        listaBiglietti = new LinkedList<Biglietto>();
    }
    public Ordine(int idOrdine){
        this.idOrdine = idOrdine;
        listaBiglietti = new LinkedList<Biglietto>();
    }

    
    public void add(Biglietto b){
        listaBiglietti.add(b);
        prezzoTotale+=b.getImporto();
    }
    
    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public float getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(float prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public LinkedList<Biglietto> getListaBiglietto() {
        return listaBiglietti;
    }

    public void setListaBiglietto(LinkedList<Biglietto> listaBiglietti) {
        this.listaBiglietti = listaBiglietti;
    }

    @Override
    public String toString() {
        return "Ordine{" + "idOrdine=" + idOrdine + ", prezzoTotale=" + prezzoTotale + ", listaBiglietti=" + listaBiglietti + '}';
    }
    
    
}
