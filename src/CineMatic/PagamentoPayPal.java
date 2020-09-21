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
public class PagamentoPayPal implements ModalitaPagamento {

    @Override
    public void paga(float amount) {
              System.out.println("Pagamento di "+amount+" tramite PayPal...");
              System.out.println("Pagamento Eseguito");
    }
    
}
