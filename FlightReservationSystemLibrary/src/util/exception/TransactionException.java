/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Ziyue
 */
public class TransactionException extends Exception {

    /**
     * Creates a new instance of <code>TransactionException</code> without
     * detail message.
     */
    public TransactionException() {
    }

    /**
     * Constructs an instance of <code>TransactionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TransactionException(String msg) {
        super(msg);
    }
}
