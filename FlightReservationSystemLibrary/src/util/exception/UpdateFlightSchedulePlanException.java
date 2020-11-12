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
public class UpdateFlightSchedulePlanException extends Exception {

    /**
     * Creates a new instance of <code>UpdateFlightSchedulePlanException</code>
     * without detail message.
     */
    public UpdateFlightSchedulePlanException() {
    }

    /**
     * Constructs an instance of <code>UpdateFlightSchedulePlanException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateFlightSchedulePlanException(String msg) {
        super(msg);
    }
}
