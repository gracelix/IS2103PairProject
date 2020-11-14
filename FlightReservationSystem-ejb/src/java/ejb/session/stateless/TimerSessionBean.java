/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import util.enumeration.FlightSchedulePlanType;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class TimerSessionBean implements TimerSessionBeanRemote, TimerSessionBeanLocal {

    
    @EJB
    private FlightScheduleSessionBeanLocal flightScheduleSessionBean;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //@Schedule(hour = "*", minute = "*", second = "*/30", info = "checkRecurrentFlightSchedulePlan")
    /*
    @Override
    public void checkRecurrentFlightSchedulePlan() {
        System.out.println("Timer Session Bean started\n");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date oneMonthLater = calendar.getTime();
                
        List<FlightSchedulePlan> flightSchedulePlans = flightSchedulePlanSessionBean.retrieveAllFlightSchedulePlans();
        if (flightSchedulePlans.isEmpty()) {
            System.out.println("FSP list is empty!");
        } else {
            System.out.println("theres something in the FSP list!");
        }
        
        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            List<FlightSchedule> flightSchedules = flightSchedulePlan.getFlightSchedules();
            while (true) {
                FlightSchedule lastFlightSchedule = flightSchedules.get(flightSchedules.size()-1);

                if (lastFlightSchedule.getDepartureDateTime().before(oneMonthLater)) {
                    if (flightSchedulePlan.getFlightSchedulePlanType() == FlightSchedulePlanType.RECURRENT_DAY) {
                        Integer n = flightSchedulePlan.getnDays();
                        System.out.println(n);
                        
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(lastFlightSchedule.getDepartureDateTime());
                        calendar2.add(Calendar.DAY_OF_MONTH, n);
                        
                        Date departureDate = calendar2.getTime();
                        Date estimatedFlightDuration = lastFlightSchedule.getEstimatedFlightDuration();
                        Date arrivalDate;
                        
                        double timeZoneDiff = flightSchedulePlan.getFlight().getFlightRoute().getDestinationAirport().getTimeZone() - flightSchedulePlan.getFlight().getFlightRoute().getOriginAirport().getTimeZone();
        
                        Integer timeZoneHours = (int) timeZoneDiff;
                        Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.setTime(estimatedFlightDuration);
                        Integer flightHours = calendar3.get(Calendar.HOUR_OF_DAY);
                        Integer flightMinutes = calendar3.get(Calendar.MINUTE);


                        calendar3.setTime(departureDate);
                        calendar3.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
                        calendar3.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
                        arrivalDate = calendar3.getTime();
                        
                        FlightSchedule flightSchedule = new FlightSchedule(departureDate, estimatedFlightDuration, arrivalDate);
                        try {
                            Long flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(flightSchedule, flightSchedulePlan.getFlightSchedulePlanId());
                            FlightSchedule retrieveFlightScheduleById = flightScheduleSessionBean.retrieveFlightScheduleById(flightScheduleId);
                            System.out.println("Flight Schedule " + retrieveFlightScheduleById.getFlightScheduleId() + " has been created for Flight Schedule Plan " + flightSchedulePlan.getFlightSchedulePlanId() + ".\n");
                                    
                        } catch (FlightSchedulePlanNotFoundException | CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException ex) {
                            System.out.println("There was an error creating the flight schedule.");
                        }
                        flightSchedules.add(flightSchedule);
                        
                    }
                } else {
                    break;
                }
            }
        }
    }
*/
}
