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
public class FlightRouteNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>FlightRouteNotFoundException</code>
     * without detail message.
     */
    public FlightRouteNotFoundException() {
    }

    /**
     * Constructs an instance of <code>FlightRouteNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightRouteNotFoundException(String msg) {
        super(msg);
    }
}
