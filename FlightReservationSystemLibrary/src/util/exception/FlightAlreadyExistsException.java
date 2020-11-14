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
public class FlightAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>FlightAlreadyExistsException</code>
     * without detail message.
     */
    public FlightAlreadyExistsException() {
    }

    /**
     * Constructs an instance of <code>FlightAlreadyExistsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightAlreadyExistsException(String msg) {
        super(msg);
    }
}
