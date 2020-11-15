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
public class InvalidDateFormatException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDateFormatException</code> without
     * detail message.
     */
    public InvalidDateFormatException() {
    }

    /**
     * Constructs an instance of <code>InvalidDateFormatException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDateFormatException(String msg) {
        super(msg);
    }
}
