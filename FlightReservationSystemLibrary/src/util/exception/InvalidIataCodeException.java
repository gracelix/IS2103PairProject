/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author GraceLi
 */
public class InvalidIataCodeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidIataCodeException</code> without
     * detail message.
     */
    public InvalidIataCodeException() {
    }

    /**
     * Constructs an instance of <code>InvalidIataCodeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidIataCodeException(String msg) {
        super(msg);
    }
}
