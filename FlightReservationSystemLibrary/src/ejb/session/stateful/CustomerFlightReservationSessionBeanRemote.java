/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.FlightSchedule;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ziyue
 */
@Remote
public interface CustomerFlightReservationSessionBeanRemote {

    public void remove();

    public List<FlightSchedule> searchFlights(String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Integer numberOfTravellers);

    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers);
    
}
