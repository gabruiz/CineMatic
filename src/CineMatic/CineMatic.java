package cinematic;

import CineMatic.ModalitaPagamento;
import CineMatic.Ordine;
import CineMatic.PagamentoBitcoin;
import CineMatic.PagamentoCarta;
import CineMatic.PagamentoPayPal;
import CineMatic.RecensioneFactory;
import db_Online.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/*
 * To change this licinse header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabri
 */
public class CineMatic {
    
    public HibernateUtilities hibernateUtilities;
    
    private LinkedList<Cinema> listaCinema;
    private LinkedList<Cinema> lc;
    private LinkedList<Film> listaFilm;
    private LinkedList<Cliente> listaClienti;
    private LinkedList<Tipopromozione> listaTipopromozione;
    private LinkedList<Omaggio> listaOmaggi;
    private LinkedList<Biglietto> listaBiglietti;
      
    LinkedList<String> postiSelezionati;
    
    public LinkedList<Biglietto> getListaBiglietti() {
        return listaBiglietti;
    }
    
    private ModalitaPagamento pagamentoStrategy;

    public Cinema getCurrent_cinema() {
        return current_cinema;
    }

    public void setCurrent_cinema(Cinema current_cinema) {
        this.current_cinema = current_cinema;
    }

    public void setCurrent_film(Film current_film) {
        this.current_film = current_film;
    }

    public Film getCurrent_film() {
        return current_film;
    }

    public void setCurrent_cliente(Cliente current_cliente) {
        this.current_cliente = current_cliente;
    }

    public Cliente getCurrent_cliente() {
        return current_cliente;
    }
    
    // istanze correnti
    private Cinema current_cinema;
    private Film current_film;
    private Cliente current_cliente;
    private float current_prezzo;
    public Ordine current_order;
    
    
    RecensioneFactory recensioneFactory;

    public void setCurrent_prezzo(float current_prezzo) {
        this.current_prezzo = current_prezzo;
    }

    public float getCurrent_prezzo() {
        return current_prezzo;
    }
    
    


    public LinkedList<Cinema> getListaCinema() {
        return listaCinema;
    }
    
    public LinkedList<Film> getListaFilm(){
        return listaFilm;
    }
    
    public LinkedList<Biglietto> getListaBiglietto(){
        return listaBiglietti;
    }
    
    private static CineMatic singleton = null;
    /**
     * @param args the command line arguments
     */
    private CineMatic (){
        System.out.println("vai vai");
        hibernateUtilities = new HibernateUtilities();
        hibernateUtilities.openSession();
        
        postiSelezionati = new LinkedList<String>();
        
        /* Carica la Lista dei Cinema */
        List<Cinema> lc = hibernateUtilities.find("cinema","Cinema");
        listaCinema = new LinkedList<Cinema>();
        System.out.println("Caricamento lista cinema...");
        for (Cinema c : lc) {
            c.caricaListaSala();
            c.caricaListaFilmP();
            c.caricaListaPromozioni();
            c.caricaListaRecensioni();
            listaCinema.add(c);
            //System.out.println(c);
        }
        
        /* Carica la Lista dei Tipi Promozione */
        List<Tipopromozione> ltp = hibernateUtilities.find("tipopromozione","Tipopromozione");
        listaTipopromozione = new LinkedList<Tipopromozione>();
        
            System.out.println("Caricamento lista TipoPromozione...");
            for (Tipopromozione tp : ltp) {

                listaTipopromozione.add(tp);
                System.out.println(tp);
            }
        
        
            /* Carica la Lista dei Film */
            List<Film> lf = hibernateUtilities.find("film","Film");
            listaFilm = new LinkedList<Film>();
            
            System.out.println("Caricamento lista film...");
            for (Film f : lf) {
                f.caricaListaRecensioni();
                listaFilm.add(f);
                //System.out.println(f);
            }
        
            /* Carica la Lista dei Clienti */
            List<Cliente> lcli = hibernateUtilities.find("cliente","Cliente");
            listaClienti = new LinkedList<Cliente>();
            
            System.out.println("Caricamento lista clienti...");
            for (Cliente cli: lcli) {
                listaClienti.add(cli);
                System.out.println(cli);
            }
            
            /* Carica la Lista omaggi */
            List<Omaggio> lo = hibernateUtilities.find("omaggio","Omaggio");
            listaOmaggi = new LinkedList<Omaggio>();
            
            System.out.println("Caricamento lista omaggi...");
            for (Omaggio o: lo) {
                listaOmaggi.add(o);
                System.out.println(o);
            }
            
        List<Biglietto> lb = hibernateUtilities.find("biglietto","Biglietto");
        listaBiglietti = new LinkedList<Biglietto>();
        System.out.println("Caricamento lista Biglietto...");
        for (Biglietto b : lb) {
            
            listaBiglietti.add(b);
            //System.out.println(c);
        }
        
        recensioneFactory= new RecensioneFactory();
    
    }
    
    public static synchronized CineMatic getInstance() {
        if (singleton == null) {
            singleton = new CineMatic();
            System.out.println("CineMatic istanziato");
        }
        else System.out.println("E' gia' presente un'istanza di CineMatic");
        return singleton;
    }
    
        
    public void inserimentoNuovoCinema(int idGestore, String nomeCinema,String indirizzo,String citta,String provincia,String telefono){
        Cinema c;
        int idCinema = 1;
        try{
            if (listaCinema.isEmpty()){
                System.out.println("Nessun cinema presente nel sistema");                    
            }
            else{
                System.out.println("Sono giÃ  presenti dei cinema");

                Cinema ultimoCinema=listaCinema.getLast();
                int ultimoIdCinema = ultimoCinema.getIdCinema();
                idCinema=calcoloId(ultimoIdCinema);
            }
            c=new Cinema(idCinema, idGestore, nomeCinema, indirizzo, citta, provincia, telefono);   
            listaCinema.add(c);
            hibernateUtilities.save(c);          
            System.out.println("Cinema Creato");
            
            
        } catch(Exception e){
            e.printStackTrace();
        }       
    }
    
    public void inserimentoNuovoFilm(String titolo, int anno, String trama, String cast, String regista){
        Film f;
        int idFilm = 1;
        try{
            if (listaFilm.isEmpty()){

                System.out.println("Nessun Film presente nel sistema");
                

            }
            else{
                System.out.println("Sono giÃ  presenti dei film");
                
                int ultimoIdFilm;
                Film ultimoFilm;
                ultimoFilm = listaFilm.getLast();
                ultimoIdFilm = ultimoFilm.getIdFilm();
                idFilm = calcoloId(ultimoIdFilm);
            }
            f = new Film(idFilm, titolo, anno, trama, cast, regista);
            listaFilm.add(f);
            hibernateUtilities.save(f);  
            System.out.println("Film Creato");
            
        }
        catch(Exception e) {
            System.out.println(e);
        }       
    }
    
    public int autenticazioneGestoreCinema(int idCinema){
        
        String tabella = "cinema";
        List<Cinema> lc=hibernateUtilities.find(tabella,"Cinema");
        for (Cinema c : lc){
            if (idCinema == c.getIdCinema()){
                current_cinema = c;
                System.out.println("Autenticato");
                return 1;
            }
        }
        System.out.println("Cinema non trovato");
        return 0;
    }
    
    public void inserimentoNuovaSala(String nomeSala, int numeroFile, int numeroPosti){
       
       int ultimoIdSala = current_cinema.getUltimoIdSala();
       int idSala = calcoloId(ultimoIdSala);
       
       current_cinema.creaSala(idSala, current_cinema.getIdCinema(), nomeSala, numeroFile, numeroPosti);
    }
    
    public void inserimentoFilmProiettato(String nomeSala, String titolo, String ora, float prezzo, Boolean tridimensionale, Boolean anteprima) {
        int ultimoIdFilmProiettato = current_cinema.getUltimoIdFilmProiettato();
        int idFilmProiettato = calcoloId(ultimoIdFilmProiettato);
        int postiDisponibili = current_cinema.getPosti(nomeSala);
        current_cinema.creaFilmProiettato(idFilmProiettato, nomeSala,titolo,ora,prezzo,tridimensionale,anteprima,postiDisponibili);
    }
    
    
    
    
    
    
     /* RICERCA */
    public LinkedList<Film> ricercaFilm (String titolo){
        LinkedList<Film> lf = new LinkedList<Film>();
        for (Film f : listaFilm){
            if(f.getTitolo().toUpperCase().contains(titolo.toUpperCase())){
                lf.add(f);
                System.out.println("ho aggiunto "+f);
            }
        }
       return lf;
    }
    
    //uc5
    public Film selezioneFilm(int idFilm){
        for (Film f : listaFilm){
            if(idFilm == f.getIdFilm()){
                current_film = f;
                return f;		
            }
        }	
        return null;
    }
  /*  public void setAnnoFilmP (int anno){
        this.anno = anno;
        System.out.println("ANNO DEL FILM SCELTO "+anno);
    }*/
    
    /* controllare i sequence diagram */
    public LinkedList<Cinema> ricercaFilmProiettato(String pv){
        String titolo = current_film.getTitolo();
        LinkedList<Cinema> lcp = new LinkedList<Cinema>();
        LinkedList<Cinema> lc = new LinkedList<Cinema>();
        Filmproiettato fp;
        
        /* filtro per provincia */
        for (Cinema c : listaCinema){
            if(pv.equalsIgnoreCase(c.getProvincia()))
                lcp.add(c);
        }
        for (Cinema c2 : lcp){
            fp = c2.getFilmProiettato(titolo);
            
            //System.out.println("lista film proiettati: "+fp);
            //System.out.println("Cerco il film dal titolo: "+titolo);
            if (fp!=null)
                if(fp.getId().getIdCinema() == c2.getIdCinema()){
                    lc.add(c2);
                    
            }
        }
        return lc;
    }
    
    public LinkedList<Cinema> ricercaCinema (String provincia){
        lc = new LinkedList<Cinema>();
        for (Cinema c : listaCinema){
            if (provincia.equalsIgnoreCase(c.getProvincia()))
                lc.add(c);
        }
        return lc;
    }
    
    
    //uc 7
    
    public Cinema selezionaCinema(int idCinema){
        for (Cinema c : lc){
            if(idCinema == c.getIdCinema()){
                return c;	
            }
        }	
        
        return null;
    }
    
    /* CAMBIARE I SEQUENCE DIAGRAM */
    public LinkedList<Filmproiettato> ricercaPalinsesto (int idCinema){
        
        LinkedList<Filmproiettato> lfp = new LinkedList<Filmproiettato>();
        for (Cinema c : listaCinema){
            if (c.getIdCinema() == idCinema)
                lfp = c.getPalinsesto();
        }
        return lfp;
    }
    
    public LinkedList<Poltrona> acquistoBiglietto (int idC , int idFilmProiettato){
        Cinema c = findCinemaById(idC);
        setCurrent_cinema(c);
        System.out.println("Acquisto Biglietto per: "+idFilmProiettato+" del Cinema: "+c.getNomeCinema());
        
        int [] fileAndPosti;
        LinkedList<Poltrona> lp = new LinkedList<Poltrona>();
   
            // file and posti
             fileAndPosti= c.getFileAndPosti(idFilmProiettato);
             System.out.println("nFile: "+fileAndPosti[0]);
             System.out.println("nPosti: "+fileAndPosti[1]);
             
             int nFile = fileAndPosti[0];
             int nPosti = fileAndPosti[1];
             
            // lista delle poltrone della sala
            lp = c.getListaPoltrone(idFilmProiettato);
            System.out.println("Lista Poltrone da get su cinema: "+lp);
            
            //current_idFilmProiettato = idFilmProiettato;
            //current_idCinema = c.getIdCinema();
            //current_idSala = c.getSalaByIdFp(idFilmProiettato).getId().getIdSala();
            
        current_order = new Ordine();
        return lp;
    }
    
    public void selezionaPoltrona(PoltronaId pId){        
        System.out.println("hello!");
        Cinema c = getCinemaById(pId.getIdCinema());
        c.selezionaPoltrona(pId);
    }
    
    
    
    private int calcoloId(int ultimoId){
       return ++ultimoId; 
    }

    private Cinema findCinemaById(int idC) {
        
        for (Cinema c : listaCinema)
            if (c.getIdCinema() == idC)
                return c;
        return null;
    }

    private Filmproiettato getFilmPById(Cinema c, int idFilmProiettato) {
        LinkedList<Filmproiettato> lfp = c.getListaFilmP();
        
        for (Filmproiettato fp : lfp)
            if (fp.getId().getIdFilmProiettato() == idFilmProiettato)
                return fp;
        return null;
    }

    private Cinema getCinemaById(int idCinema) {
       
        for (Cinema c: listaCinema)
            if (c.getIdCinema() == idCinema)
                return c;
        return null;
    }
    
    public LinkedList<Omaggio> assegnaCliente (int idCliente, int idFilmProiettato){
        LinkedList<Omaggio> elencoOmaggi = new LinkedList<Omaggio>();
            
        System.out.println("SONO SU ASSEGNA CLIENTE " +idFilmProiettato);
        Cliente cli = getClienteById(idCliente);
        current_cliente = cli;
        System.out.println("stampo il cinema corrente "+current_cinema);
        String categoria = cli.getCategoria();
                Filmproiettato fp = current_cinema.getFilmProiettatoById(idFilmProiettato);
                float prezzo = fp.getPrezzo();
                System.out.println("Prezzo intero: "+prezzo);
            /* Categoria = 0 indica che il cliente non ha diritto a nessuno sconto */
            if (!categoria.equals("0")){
                // Calcolo sconto
                System.out.println("Il cliente appartiene ad una categoria");
                Tipopromozione tipoPromo = getTipoPromoBycat(Integer.parseInt(categoria));

                int idTipoPromo = tipoPromo.getIdPromozione();
                System.out.println("idTipoPromo: "+idTipoPromo);

                Boolean isPresent = current_cinema.checkPromo(idTipoPromo);

                System.out.println("Presente: "+isPresent);

                if (isPresent){
                    System.out.println("Il cinema offre uno sconto a quella categoria");
                    int sconto = tipoPromo.getSconto();

                    prezzo = calcoloPrezzo(prezzo , sconto);
                }
                
            int puntiCliente = cli.getPunti();
            for (Omaggio o : listaOmaggi)
                if(o.getCosto()<=puntiCliente)
                    elencoOmaggi.add(o);
            
                System.out.println("Punti: "+puntiCliente);
            }
            setCurrent_prezzo(prezzo);
            System.out.println("prezzo finale biglietto: "+prezzo);
            return elencoOmaggi;
    }
    
    
    public String selezioneOmaggio(int idOmaggio){
        Omaggio o = getOmaggioById(idOmaggio);
        int costoPunti = o.getCosto();
        String omaggio = o.getNomeOmaggio();
        int punti = current_cliente.getPunti();
        int nuoviPunti = scalaPunti(punti, costoPunti);
        if(nuoviPunti<0)
            nuoviPunti = 0;
        current_cliente.setPunti(nuoviPunti);
        hibernateUtilities.save(current_cliente);
        
        return o.getNomeOmaggio();
    }
    
    public void creaBiglietto (int idFilmProiettato, String fila, String posto, String omaggio){
        int idBiglietto = 0;
        int ultimoIdBiglietto = 0;
        if(!listaBiglietti.isEmpty()){
            Biglietto b = listaBiglietti.getLast();
            ultimoIdBiglietto = b.getIdBiglietto();
        }
        idBiglietto= calcoloId(ultimoIdBiglietto);
        
        Filmproiettato fp = current_cinema.getFilmProiettatoById(idFilmProiettato);
        String ora = fp.getOra();
        String titolo = fp.getTitolo();
        String nomeCinema = current_cinema.getNomeCinema();
        String nomeSala = fp.getNomeSala();
        int idCliente = current_cliente.getIdCliente();
        
        float prezzo = getCurrent_prezzo();
        String filaPosto = fila+posto;    
       
        
        Biglietto bi = new Biglietto (idBiglietto, idCliente, ora, titolo, nomeCinema, nomeSala, prezzo, filaPosto, omaggio);
        System.out.println(bi);
        listaBiglietti.add(bi);
        hibernateUtilities.save(bi);
        
        // Aggiunge il biglioetto all'ordine corrente
        if (current_order.getListaBiglietto().isEmpty() || current_order.getListaBiglietto() == null)
            current_order = new Ordine(bi.getIdBiglietto());
        current_order.add(bi);
    }

    public Ordine getCurrent_order() {
        return current_order;
    }

    private Omaggio getOmaggioById(int idOmaggio){
        for (Omaggio o : listaOmaggi){
            if(o.getIdOmaggio() == idOmaggio)
                return o;
        }
        return null;
    }
    private Cliente getClienteById(int idCliente) {
        for (Cliente c : listaClienti)
            if (c.getIdCliente() == idCliente)
                return c;
        return null;
    }

    private Tipopromozione getTipoPromoBycat(int categoria) {

            for (Tipopromozione tp: listaTipopromozione )
                if (tp.getCategoria() == categoria)
                    return tp;
            return null;
    }

    private float calcoloPrezzo(float prezzoIntero, int sconto) {
            return (prezzoIntero - (prezzoIntero*sconto/100));
    }
    
    private int scalaPunti (int punti, int costoPunti){
        return (punti-costoPunti);
    }
    
    public LinkedList<String> conferma(){
        LinkedList<String> metodiPagamento = new LinkedList<String>();
            metodiPagamento.add("Carta di Credito");
            metodiPagamento.add("PayPal");
            metodiPagamento.add("Bitcoin");
            
        return metodiPagamento;
    }
    
    public void selezionaModalitaPagamento(String metodoPagamento){
        
        ModalitaPagamento concreteStrategy;
        switch (metodoPagamento){
            case "Carta di Credito":
                concreteStrategy = new PagamentoCarta();
                this.pagamentoStrategy = concreteStrategy;
                break;
            case "PayPal":
                concreteStrategy = new PagamentoPayPal();
                this.pagamentoStrategy = concreteStrategy;
                break;
            case "Bitcoin":
                concreteStrategy = new PagamentoBitcoin();
                this.pagamentoStrategy = concreteStrategy;
                break;
        }
    }
    
    public void effettuaPagamento (float amount){
        pagamentoStrategy.paga(amount);
    }
    
    
    
    public void inserisciRecensioneFilm(int idCliente, int punteggio,String recensioneTesto){
        int ultimoIdRecensioneFilm = current_film.getUltimoIdRecensione();
        int idRecensione = calcoloId(ultimoIdRecensioneFilm);
        current_film.creaRecensione(idRecensione , idCliente , recensioneTesto , punteggio);      
    }
    
    public void inserisciRecensioneCinema(int idCliente, int punteggio,String recensioneTesto){
        int ultimoIdRecensioneCinema = current_cinema.getUltimoIdRecensione();
        int idRecensione = calcoloId(ultimoIdRecensioneCinema);    
        current_cinema.creaRecensione(idRecensione , idCliente , recensioneTesto , punteggio);    
    }
	
	
	    public LinkedList<Tipopromozione> gestionePromozioneCinema(){
       
            return listaTipopromozione;
    }

    public void selezionaPromozione(int idTipoPromozione){
            current_cinema.selezionaPromozione(idTipoPromozione);
    }


    
    public LinkedList<Filmproiettato> gestioneAnteprimaCinema(int idCinema){
            LinkedList<Filmproiettato> lfpa;
            lfpa = new LinkedList<Filmproiettato>();
            for (Cinema c : listaCinema){
                    if(c.getIdCinema()==idCinema)
                            current_cinema = c;
            }

            lfpa = current_cinema.getListaFilmProiettatiAnteprima();
            return lfpa;
    }

    public int selezionaFilmAnteprima(int idFilmProiettato){
            int postiDisponibili = 0;
            postiDisponibili = current_cinema.getPostiFilmProiettato(idFilmProiettato);

            return postiDisponibili;
    }

    public void selezionaPosti(int numeroPosti){
            current_cinema.selezionaPosti(numeroPosti);
            System.out.println("Posti selezionati");
    }
    
	
}

