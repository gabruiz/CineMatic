package cinematic;

import db_Online.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.spi.ServiceRegistry;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 *
 * @author gabri
 */
public class HibernateUtilities {
    
   
    private static final Logger logger = Logger.getLogger(HibernateUtilities.class.getName());

    private static Session session;

    
    
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    
    
    private static SessionFactory configureSessionFactory() {
        
        try{
            Configuration cfg=new Configuration().configure();
            StandardServiceRegistryBuilder builder= new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());

            SessionFactory sessionFactory= cfg.buildSessionFactory(builder.build());
                    return sessionFactory;
        }catch(HibernateException e){
            System.out.println(e);
            return null;
        }

    }

    

    
    public void openSession()
    {
        try{
            if(session == null)
                session = getSessionFactory().openSession();
            if (!session.isOpen())
                session = getSessionFactory().openSession();
        }catch(HibernateException e)
        {
            e.printStackTrace();
            logger.log(Level.WARNING, "Impossibile aprire la sessione con il database:\n{0}", e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory=configureSessionFactory();
        return sessionFactory;
    }
    
    public void shutdown() {
    	
        try{
            // Close caches and connection pools
            getSessionFactory().close();
        }catch(HibernateException e)
        {
            logger.log(Level.WARNING, "Impossibile chiudere la sessione con il database:\n{0}", e);
        }
    }
    
    public Session getSession()
    {
        try{
            return session;
        }catch(Exception e){System.out.println(e);}
        return null;
    }
    
    /**
     * Effettua una ricerca in archivio.
     * 
     * @param tabella
     * @param condizione
     * @return 
     */
   
    public List find(String tabella, String choice){
            Query query;
            List result = null;
            switch(choice){
                case "Cinema":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Cinema.class);
                    result = query.list();
                    break;
                case "Film":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Film.class);
                    result = query.list();
                    break;
                case "Sala":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Sala.class);
                    result = query.list();
                    break;
                case "Poltrona":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Poltrona.class);
                    result = query.list();
                    break;          
                case "Biglietto":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Biglietto.class);
                    result = query.list();
                    break;
                case "Filmproiettato":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Filmproiettato.class);
                    result = query.list();
                    break;
                case "Gestorecinema":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Gestorecinema.class);
                    result = query.list();
                    break;
                case "Cliente":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Cliente.class);
                    result = query.list();
                    break;
                case "Promozione":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Promozione.class);
                    result = query.list();
                    break; 
                case "Tipopromozione":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Tipopromozione.class);
                    result = query.list();
                    break; 
                case "Omaggio":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Omaggio.class);
                    result = query.list();
                    break; 
                case "RecensioneFilm":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Recensionefilm.class);
                    result = query.list();
                    break; 
                case "RecensioneCinema":
                    query = session.createSQLQuery("select * from "+tabella).addEntity(Recensionecinema.class);
                    result = query.list();
                    break; 
            }
            
            return result;
    }

  
    /**
     * Effettua un inserimento in archivio. Ritorna l'id dell'elemento inserito.
     * 
     * @param element
     * @return 
     */
    public void save(Object element)
    {

        
        try{
            session.beginTransaction();
            session.save(element);
            session.getTransaction().commit();
                    
        }catch(HibernateException e)
        {
           e.printStackTrace();
        }


    }
    
    
    /**
     * Aggiorna un elemento in archivio.
     * 
     * @param element 
     */
    public void aggiornaElemento(Object element)
    {
        try{
            session.beginTransaction();
            session.saveOrUpdate(element);
            session.getTransaction().commit();
        }catch(HibernateException e)
        {
            logger.log(Level.WARNING, "Errore durante l'aggiornamento dei dati in archivio:\n{0}", e);
        }
    }
    
    
    /**
     * Rimuovi un elemento dall'archivio.
     * 
     * @param element 
     */
    public void rimuoviElemento(Object element)
    {
        try{
            session.beginTransaction();
            session.delete(element);
            session.getTransaction().commit();
        }catch(HibernateException e)
        {
            logger.log(Level.WARNING, "Errore durante la rimozione dei dati dall'archivio:\n{0}", e);
        }
    }
}