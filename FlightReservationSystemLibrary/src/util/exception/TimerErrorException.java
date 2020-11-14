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
public class TimerErrorException extends Exception {

    /**
     * Creates a new instance of <code>TimerErrorException</code> without detail
     * message.
     */
    public TimerErrorException() {
    }

    /**
     * Constructs an instance of <code>TimerErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TimerErrorException(String msg) {
        super(msg);
    }
}
