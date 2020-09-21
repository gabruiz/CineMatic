/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CineMatic;

import cinematic.CineMatic;
import db_Online.Biglietto;
import db_Online.Cinema;
import db_Online.Cliente;
import db_Online.Film;
import db_Online.Filmproiettato;
import db_Online.Omaggio;
import db_Online.Poltrona;
import db_Online.Recensionecinema;
import db_Online.Recensionefilm;
import db_Online.Sala;
import java.text.ParseException;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tina Conti
 */
public class CineMaticTest {
    
    CineMatic instance;
    
    public CineMaticTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class CineMatic.
     */
    @Test
    public void testGetInstance() {
        CineMatic expResult = CineMatic.getInstance();
        CineMatic result = CineMatic.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of inserimentoNuovoCinema method, of class CineMatic.
     */
    @Test
    public void testInserimentoNuovoCinema() {
        int idGestore = 1020;
        String nomeCinema = "Liga";
        String indirizzo = "via Papa Giovanni";
        String citta = "Messina";
        String provincia = "ME";
        String telefono = "0909761622";
        instance = CineMatic.getInstance();
        instance.inserimentoNuovoCinema(idGestore, nomeCinema, indirizzo, citta, provincia, telefono);
        
        Cinema cn = instance.getListaCinema().getLast();
        assertNotNull(cn);
        
        assertEquals((long)cn.getIdGestore(), (long)idGestore);
        assertSame(cn.getNomeCinema(),nomeCinema);
        assertSame(cn.getIndirizzo(),indirizzo);
        assertSame(cn.getCitta(), citta);
        assertSame(cn.getProvincia(),provincia);
        assertSame(cn.getTelefono(), telefono);
    }

    /**
     * Test of inserimentoNuovoFilm method, of class CineMatic.
     */
    @Test
    public void testInserimentoNuovoFilm() {
        String titolo = "Old City";
        int anno = 1999;
        String trama = "Una città nasconde un terribile segreto";
        String cast = "Sarah Jessica Parker, Jason Lewis, Kim Cattrall, Kristin Davis e Cynthia Nixon";
        String regista = "Michael Patrick King";
        instance = CineMatic.getInstance();
        instance.inserimentoNuovoFilm(titolo, anno, trama, cast, regista);
        
        Film f = instance.getListaFilm().getLast();
        assertNotNull(f);
        
        assertSame(f.getTitolo(), titolo);
        assertEquals((long)f.getAnno(), anno);
        assertSame(f.getTrama(), trama);
        assertSame(f.getCast(), cast);
        assertSame(f.getRegista(), regista);
    }

    /**
     * Test of autenticazioneGestoreCinema method, of class CineMatic.
     */
    @Test
    public void testAutenticazioneGestoreCinema() {
        instance = CineMatic.getInstance();
        instance.inserimentoNuovoCinema(1020, "Liga", "via Papa Giovanni", "Messina", "ME", "0909761622");
        
        int res = instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());
        assertEquals((long)res, 1);
    }

    /**
     * Test of inserimentoNuovaSala method, of class CineMatic.
     */
    @Test
    public void testInserimentoNuovaSala() {
        instance = CineMatic.getInstance();
        creaCinema();
        String nomeSala = "Oceano";
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());
        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);
       
        Cinema cin = instance.getListaCinema().getLast();
        Sala s= cin.getListaSala().getLast();
        assertNotNull(s);
        
        assertSame(s.getNomeSala() ,nomeSala);
        assertEquals((long)s.getNumeroPosti(), numeroPosti);
        
    }

    /**
     * Test of inserimentoFilmProiettato method, of class CineMatic.
     * @throws java.lang.Exception
     */
    @Test
    public void testInserimentoFilmProiettato() throws Exception {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
        //inserisco un cinema
        creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        Cinema cn = instance.getListaCinema().getLast();
        assertNotNull(cn);
        
        Filmproiettato fp = cn.getFilmProiettato(titolo);
        assertNotNull(fp);
        
        assertSame(fp.getNomeSala(), nomeSala);
        assertSame(fp.getTitolo(), titolo);
        assertSame(fp.getOra(), ora);       
        assertSame(fp.isTridimensionale(), tridimensionale);
        assertSame(fp.isAnteprima(), anteprima);           
    }
    
     /**
     * Test of ricercaFilm method, of class CineMatic.
     * @throws java.lang.Exception
     */
    @Test
    public void testRicercaFilm() throws Exception {
        String titolo = "Old City";
        int anno = 1999;
        String trama = "Una città nasconde un terribile segreto";
        String cast = "Sarah Jessica Parker, Jason Lewis, Kim Cattrall, Kristin Davis e Cynthia Nixon";
        String regista = "Michael Patrick King";
        instance = CineMatic.getInstance();
        instance.inserimentoNuovoFilm(titolo, anno, trama, cast, regista);
               
        LinkedList<Film> listaFilm = instance.ricercaFilm(titolo);
                            
        assertFalse(listaFilm.isEmpty());      
    }
    
     /**
     * Test of ricercaFilm method, of class CineMatic.
     * @throws java.lang.Exception
     */
    @Test
    public void testRicercaFilmProiettato() throws Exception {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
        creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        
        LinkedList<Cinema> listaCinema = instance.ricercaFilmProiettato("ME");
                            
        assertFalse(listaCinema.isEmpty());      
    }
    
    /**
     * Test of ricercaFilm method, of class CineMatic.
     * @throws java.lang.Exception
     */
    @Test
    public void testRicercaCinema() throws Exception {
        String provincia ="ME";
        instance =  CineMatic.getInstance();        
        instance.inserimentoNuovoCinema(1020, "Liga", "via Papa Giovanni", "Messina", provincia, "0909761622");  
        LinkedList<Cinema> listaCinema = instance.ricercaCinema(provincia);                            
        assertFalse(listaCinema.isEmpty());      
    }
    
     /**
     * Test of ricercaFilm method, of class CineMatic.
     * @throws java.lang.Exception
     */
    @Test
    public void testRicercaPalinsesto() throws Exception {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
       
        creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        
        LinkedList<Filmproiettato> listaFilm = instance.ricercaPalinsesto(instance.getListaCinema().getLast().getIdCinema());
        assertFalse(listaFilm.isEmpty());   
    }
    
    /**
     * Test of acquistoBiglietto method, of class CineMatic.
     * @throws java.text.ParseException
     */
    @Test
    public void testAcquistoBiglietto() throws ParseException {     
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
         creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        Cinema cn =instance.getListaCinema().getLast();       
        int idC = cn.getIdCinema();
        
        Filmproiettato fp = cn.getFilmProiettato(titolo);
        int  idFilmProiettato = cn.getFilmProiettato(titolo).getId().getIdFilmProiettato();
               
        LinkedList<Poltrona> result = instance.acquistoBiglietto(idC, idFilmProiettato);
        
        assertFalse(result.isEmpty());     
          
    }

    /**
     * Test of assegnaCliente method, of class CineMatic.
     * @throws java.text.ParseException
     */
    @Test
    public void testAssegnaCliente() throws ParseException {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();       
        creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        Cinema cn =instance.getListaCinema().getLast();       
        int idC = cn.getIdCinema();
               
        int  idFilmProiettato = cn.getFilmProiettato(titolo).getId().getIdFilmProiettato();
             
        int idCliente = 1;             
        LinkedList<Omaggio> result = instance.assegnaCliente(idCliente, idFilmProiettato);
        assertFalse(result.isEmpty());     
    }

    /**
     * Test of selezioneOmaggio method, of class CineMatic.
     */
    @Test
    public void testSelezioneOmaggio() {     
        
        int idOmaggio = 1;       
        String expResult = "Pop Corn";
        instance =  CineMatic.getInstance();     
        instance.setCurrent_cliente(new Cliente());
        String result = instance.selezioneOmaggio(idOmaggio);
        assertEquals(expResult, result);        
    }

    /**
     * Test of creaBiglietto method, of class CineMatic.
     * @throws java.text.ParseException
     */
    @Test
    public void testCreaBiglietto() throws ParseException {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
        creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        
        Cinema cn =instance.getListaCinema().getLast();       
        int idC = cn.getIdCinema();
               
        int  idFilmProiettato = cn.getFilmProiettato(titolo).getId().getIdFilmProiettato(); 
        instance.acquistoBiglietto(idC, idFilmProiettato);
        
        String fila = "1";
        String posto = "1";
        String omaggio = "Pop Corn";
       
        instance.assegnaCliente(1, idFilmProiettato);
        instance.creaBiglietto(idFilmProiettato, fila, posto, omaggio);
        
        LinkedList<Biglietto> lb = instance.getListaBiglietti();
        Biglietto b =lb.getLast();
        
        assertNotNull(b);
        
        assertSame(b.getTitolo(), titolo);            
    }

    /**
     * Test of inserisciRecensioneFilm method, of class CineMatic.
     */
    @Test
    public void testInserisciRecensioneFilm() {
        String titolo = "Old City";
        int anno = 1999;
        String trama = "Una città nasconde un terribile segreto";
        String cast = "Sarah Jessica Parker, Jason Lewis, Kim Cattrall, Kristin Davis e Cynthia Nixon";
        String regista = "Michael Patrick King";
        instance = CineMatic.getInstance();
        instance.inserimentoNuovoFilm(titolo, anno, trama, cast, regista);
        
        Film f = instance.getListaFilm().getLast();
        f.caricaListaRecensioni();
        assertNotNull(f);
        instance.setCurrent_film(f);
        
        instance.inserisciRecensioneFilm(1, 7,"buono");
        
        
        Recensionefilm r = (Recensionefilm)f.getRecensioni().getLast();
        
       assertSame(r.getContenuto(),"buono");
       
    }
    
    /**
     * Test of inserisciRecensioneCinema method, of class CineMatic.
     */
    @Test
    public void testInserisciRecensioneCinema() {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
        Cinema c = creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        instance.getListaCinema().getLast().caricaListaRecensioni();
        instance.inserisciRecensioneCinema(1, 7,"buono");
        
        
        Recensionecinema r = (Recensionecinema)c.getRecensioni().getLast();
        
       assertSame(r.getContenuto(),"buono");
       
    }
    
    private Cinema creaCinema(){
         instance.inserimentoNuovoCinema(1020, "Liga", "via Papa Giovanni", "Messina", "ME", "0909761622");
        Cinema c =instance.getListaCinema().getLast();
        c.caricaListaSala();
        c.caricaListaSala();
        c.caricaListaFilmP();
        c.caricaListaPromozioni();
        c.caricaListaRecensioni();
        return c;
    }
           
    
    /**
     * Test of gestioneAnteprimaCinema method, of class CineMatic.
     */
    @Test
    public void testGestioneAnteprimaCinema() {
        String nomeSala = "Blu";
        String titolo = "Old City";
        String ora = "18:45";
        float prezzo = 7.5F;
        Boolean tridimensionale = true;
        Boolean anteprima = true;
        instance =  CineMatic.getInstance();
        Cinema c = creaCinema();               
        int numeroPosti =  2;
        int numeroFile = 2;                
        instance.autenticazioneGestoreCinema(instance.getListaCinema().getLast().getIdCinema());        
        instance.inserimentoNuovaSala(nomeSala, numeroFile, numeroPosti);        
        instance.inserimentoFilmProiettato(nomeSala, titolo, ora, prezzo, tridimensionale, anteprima);
        instance.getListaCinema().getLast().caricaListaRecensioni();
        instance.inserisciRecensioneCinema(1, 7,"buono");
        
        
        LinkedList<Filmproiettato> fp = instance.gestioneAnteprimaCinema(c.getIdCinema());
        assertFalse(fp.isEmpty());    
    }
      
    
}
