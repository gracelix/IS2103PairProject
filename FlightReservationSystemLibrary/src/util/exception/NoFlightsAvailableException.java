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
public class NoFlightsAvailableException extends Exception {

    /**
     * Creates a new instance of <code>NoFlightsAvailableException</code>
     * without detail message.
     */
    public NoFlightsAvailableException() {
    }

    /**
     * Constructs an instance of <code>NoFlightsAvailableException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoFlightsAvailableException(String msg) {
        super(msg);
    }
}
