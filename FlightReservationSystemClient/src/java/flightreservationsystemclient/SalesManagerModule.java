/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.FlightReservationSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.SeatInventorySessionBeanRemote;
import entity.Employee;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.SeatInventory;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
public class SalesManagerModule {
    private FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    private FlightSessionBeanRemote flightSessionBeanRemote;
    private FlightReservationSessionBeanRemote flightReservationSessionBeanRemote;
    private SeatInventorySessionBeanRemote seatInventorySessionBeanRemote;
    private Employee employee;

    public SalesManagerModule() {
    }

    public SalesManagerModule(FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, FlightSessionBeanRemote flightSessionBeanRemote, FlightReservationSessionBeanRemote flightReservationSessionBeanRemote, SeatInventorySessionBeanRemote seatInventorySessionBeanRemote, Employee employee) {
        this.flightScheduleSessionBeanRemote = flightScheduleSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightReservationSessionBeanRemote = flightReservationSessionBeanRemote;
        this.seatInventorySessionBeanRemote = seatInventorySessionBeanRemote;
        this.employee = employee;
    }
    
    public void doSalesManagerMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
        
            System.out.println("*** Flight Reservation System Management :: Sales Manager ***\n");
            System.out.println("1: View Seats Inventory");
            System.out.println("2: View Flight Reservations");
            System.out.println("3: Logout\n");

            response = 0;
            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = sc.nextInt();

                if (response == 1) {
                    try{
                        doViewSeatsInventory();
                    } catch (FlightSchedulePlanNotFoundException ex) {
                        System.out.println("Error encountered: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    doViewFlightReservations();
                } else if (response == 3) {
                    break;
                }
            }
            if (response == 3) {
                break;
            }
        }
        
    }
    
    public void doViewSeatsInventory() throws FlightSchedulePlanNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View Seats Inventory ***\n");
        System.out.print("Enter Flight Number> ");
        String flightNumber = sc.nextLine().trim();
        List<FlightSchedulePlan> flightSchedulePlans = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlansByFlightNumber(flightNumber);
        
        
        //this chunk may produce error when recurrent come, if so dont show
        System.out.printf("%20s%32s%32s%20s\n", "Flight Schedule Id", "Departure Date", "Arrival Date", "Flight Duration");
            
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        //System.out.println(sdf.format(date));  
        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
                Long flightScheduleId = flightSchedule.getFlightScheduleId();
                FlightSchedule flightScheduleToPrint = flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId);
                System.out.printf("%20s%32s%32s%20s\n", flightScheduleId, flightScheduleToPrint.getDepartureDateTime(), flightScheduleToPrint.getArrivalDateTime(), format.format(flightScheduleToPrint.getEstimatedFlightDuration()));
            }
        }
        
        System.out.print("Enter Flight Schedule Id> ");
        Long flightScheduleId = sc.nextLong();
        FlightSchedule flightSchedule = flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId);
        //SeatInventory seatInventory = seatInventorySessionBeanRemote.retrieveSeatInventoryById(flightSchedule.g)
        List<SeatInventory> seatInventories = flightSchedule.getSeatInventories();
        Integer totalAvailable = 0;
        Integer totalReserved = 0;
        Integer totalBalance = 0;
        System.out.printf("%20s%20s%20s%20s\n", "Cabin Class", "Available Seats", "Reserved Seats", "Balance Seats");
        for (SeatInventory seatInventory : seatInventories) {
            System.out.printf("%20s%20s%20s%20s\n", seatInventory.getCabinClass().getCabinClassType(), seatInventory.getAvailableSeats(), seatInventory.getReservedSeats(), seatInventory.getBalanceSeats());
            totalAvailable += seatInventory.getAvailableSeats();
            totalReserved += seatInventory.getReservedSeats();
            totalBalance += seatInventory.getBalanceSeats();
        }
        System.out.println("------------------------");
        System.out.println("Total available seats: " + totalAvailable);
        System.out.println("Total reserved seats: " + totalReserved);
        System.out.println("Total balance seats: " + totalBalance);
    }
    
    public void doViewFlightReservations() {
        
    }
}
