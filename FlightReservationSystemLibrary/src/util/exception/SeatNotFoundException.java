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
public class SeatNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SeatNotFoundException</code> without
     * detail message.
     */
    public SeatNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SeatNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SeatNotFoundException(String msg) {
        super(msg);
    }
}
