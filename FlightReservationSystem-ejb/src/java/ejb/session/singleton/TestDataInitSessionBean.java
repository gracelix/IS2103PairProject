/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.CabinClassConfigurationSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Airport;
import entity.CabinClassConfiguration;
import entity.Employee;
import entity.Flight;
import entity.FlightRoute;
import entity.Partner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRights;
import util.enumeration.PartnerAccessRights;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.AirportNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.InvalidIataCodeException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author GraceLi
 */
@Singleton
@LocalBean
@Startup
public class TestDataInitSessionBean {

    @EJB
    private FlightSessionBeanLocal flightSessionBean;

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBean;

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBean;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBean;

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void postConstruct() {
        try {
            employeeSessionBean.retrieveEmployeeById(1l);
        } catch (EmployeeNotFoundException ex) {
            loadEmployeeData();
        }
        
        try {
            partnerSessionBean.retrievePartnerById(1l);
        } catch (PartnerNotFoundException ex) {
            loadPartnerData();
        }
        
        try {
            airportSessionBean.retrieveAirportById(1l);
        } catch (AirportNotFoundException ex) {
            loadAirportData();
        }
        
        try {
            aircraftTypeSessionBean.retrieveAircraftTypeById(1l);
        } catch (AircraftTypeNotFoundException ex) {
            loadAircraftType();
        }
        
        try {
            aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(1l);
        } catch (AircraftConfigurationNotFoundException ex) {
            loadAircraftConfiguration();
        }
        
        try {
            flightRouteSessionBean.retrieveFlightRouteById(1l);
        } catch (FlightRouteNotFoundException ex) {
            loadFlightRoute();
        }
        
        try {
            flightSessionBean.retrieveFlightById(1l);
        } catch (FlightNotFoundException ex) {
            loadFlightData();
        }
        
    }
    
    public void loadEmployeeData() {
        employeeSessionBean.createNewEmployee(new Employee("Fleet Manager", "fleetmanager", "password", EmployeeAccessRights.FLEET_MANAGER));
        
        employeeSessionBean.createNewEmployee(new Employee("Route Planner", "routeplanner", "password", EmployeeAccessRights.ROUTE_PLANNER));
        
        employeeSessionBean.createNewEmployee(new Employee("Schedule Manager", "schedulemanager", "password", EmployeeAccessRights.SCHEDULE_MANAGER));
        
        employeeSessionBean.createNewEmployee(new Employee("Sales Manager", "salesmanager", "password", EmployeeAccessRights.SALES_MANAGER));
    }
    
    public void loadPartnerData() {
        partnerSessionBean.createNewPartner(new Partner("Holiday.com", "holidaydotcom", "password", PartnerAccessRights.MANAGER));
    }
    
    public void loadAirportData() {
        
        airportSessionBean.createNewAirport(new Airport("Changi", "SIN", "Singapore", "Singapore", "Singapore", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Hong Kong", "HKG", "Chek Lap Kok", "Hong Kong", "China", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Taoyuan", "TPE", "Taoyuan", "Taipei", "Taiwan R.O.C.", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Narita", "NRT", "Narita", "Chiba", "Japan", 9.0));
        
        airportSessionBean.createNewAirport(new Airport("Sydney", "SYD", "Sydney", "New South Wales", "Australia", 8.0));
    }
    
    public void loadAircraftType() {
        aircraftTypeSessionBean.createNewAircraftType(new AircraftType("Boeing 737", 200));
        
        aircraftTypeSessionBean.createNewAircraftType(new AircraftType("Boeing 747", 400));
    }
    
    public void loadAircraftConfiguration() {
        try {
            Long aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 737 All Economy", 1, 180), 1l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 1, 30, 6, "3-3", 180), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 737 Three Classes", 3, 180), 1l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.FIRST_CLASS, 1, 5, 2, "1-1", 10), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.BUSINESS_CLASS, 1, 5, 4, "2-2", 20), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 1, 25, 6, "3-3", 150), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 747 All Economy", 1, 380), 2l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 2, 38, 10, "3-4-3", 380), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 747 Three Classes", 3, 360), 2l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.FIRST_CLASS, 1, 5, 2, "1-1", 10), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.BUSINESS_CLASS, 2, 5, 6, "2-2-2", 30), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 2, 32, 10, "3-4-3", 320), aircraftConfigurationId);
            
        } catch (AircraftTypeNotFoundException | AircraftConfigurationNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightRoute() {
        try {
            Long flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "HKG");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "HKG", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "TPE");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "TPE", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "HKG", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "HKG");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "TPE", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "TPE");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "SYD");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "SYD", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SYD", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SYD");
            
        } catch (InvalidIataCodeException | FlightRouteNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightData() {
        try {
            Long flightId = flightSessionBean.createNewFlight(new Flight("ML111"), 1l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML112"), flightId, 2l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML211"), 3l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML212"), flightId, 4l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML311"), 5l, 4l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML312"), flightId, 6l, 4l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML411"), 7l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML412"), flightId, 8l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML511"), 9l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML512"), flightId, 10l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML611"), 11l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML612"), flightId, 12l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML621"), 11l, 1l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML622"), flightId, 12l, 1l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML711"), 13l, 4l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML712"), flightId, 14l, 4l);
                    
        } catch (AircraftConfigurationNotFoundException | FlightNotFoundException | FlightRouteNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
}
