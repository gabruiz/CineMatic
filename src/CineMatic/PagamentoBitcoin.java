/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CineMatic;

/**
 *
 * @author david
 */
public class PagamentoBitcoin implements ModalitaPagamento{

    @Override
    public void paga(float amount) {
              float newAmount = conversion(amount);
              System.out.println("Pagamento di "+amount+" tramite Bitcoin ("+newAmount+" BTC)...");
              System.out.println("Pagamento Eseguito");
    }

    private float conversion(float amount) {
           return amount/10000;
    }
    
    
}
