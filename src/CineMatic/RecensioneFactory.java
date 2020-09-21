/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CineMatic;

import db_Online.Recensione;
import db_Online.Recensionecinema;
import db_Online.RecensionecinemaId;
import db_Online.Recensionefilm;
import db_Online.RecensionefilmId;

/**
 *
 * @author Tina Conti
 */
public class RecensioneFactory {

    public Recensione creaRecensione(String scelta, int idRecensione, int idCliente, int idFoC, String recensioneTesto, int punteggio){
        if (scelta.equalsIgnoreCase("Film")){
            RecensionefilmId rfId = new RecensionefilmId(idRecensione, idCliente, idFoC);
            return new Recensionefilm(rfId, recensioneTesto, punteggio);
            }
        if (scelta.equalsIgnoreCase("Cinema")){
            RecensionecinemaId rcId = new RecensionecinemaId(idRecensione, idCliente, idFoC);
            return new Recensionecinema(rcId, recensioneTesto, punteggio);
        }
        return null;
    }
    
}