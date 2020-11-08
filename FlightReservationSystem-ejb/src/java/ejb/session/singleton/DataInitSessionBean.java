/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import entity.Airport;
import entity.Employee;
import entity.Partner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRights;
import util.enumeration.PartnerAccessRights;
import util.exception.AirportNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author GraceLi
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

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
    }
    
    public void loadEmployeeData() {
        Employee fleetManager = new Employee("Fleet Manager", "fleetmanager", "password", EmployeeAccessRights.FLEET_MANAGER);
        employeeSessionBean.createNewEmployee(fleetManager);
        
        Employee routePlanner = new Employee("Route Planner", "routeplanner", "password", EmployeeAccessRights.ROUTE_PLANNER);
        employeeSessionBean.createNewEmployee(routePlanner);
        
        Employee scheduleManager = new Employee("Schedule Manager", "schedulemanager", "password", EmployeeAccessRights.SCHEDULE_MANAGER);
        employeeSessionBean.createNewEmployee(scheduleManager);
        
        Employee salesManager = new Employee("Sales Manager", "salesmanager", "password", EmployeeAccessRights.SALES_MANAGER);
        employeeSessionBean.createNewEmployee(salesManager);
        
    }
     
    public void loadPartnerData() {
        Partner partnerEmployee = new Partner("Partner Employee", "partneremployee", "password", PartnerAccessRights.EMPLOYEE);
        partnerSessionBean.createNewPartner(partnerEmployee);
        
        Partner partnerReservationManager = new Partner("Reservation Manager", "reservationmanager", "password", PartnerAccessRights.MANAGER);
        partnerSessionBean.createNewPartner(partnerReservationManager);
    }
     
    public void loadAirportData() {
        Airport singaporeChangiAirport = new Airport("Singapore Changi Airport", "SIN", "Singapore", "Singapore", "Singapore");
        airportSessionBean.createNewAirport(singaporeChangiAirport);
        
        Airport kualaLumpurInternationalAirport = new Airport("Kuala Lumpur International Airport", "KUL", "Kuala Lumpur", "Selangor", "Malaysia");
        airportSessionBean.createNewAirport(kualaLumpurInternationalAirport);
        
        Airport suvarnabhumiAirport = new Airport("Suvarnabhumi Airport", "BKK", "Bangkok", "Bangkok", "Thailand");
        airportSessionBean.createNewAirport(suvarnabhumiAirport);
        
        Airport soekarnoHattaInternationalAirport = new Airport("Soekarno Hatta International Airport", "CGK", "Jakarta", "Jakarta", "Indonesia");
        airportSessionBean.createNewAirport(soekarnoHattaInternationalAirport);
        
        Airport noiBaiInternationalAirport = new Airport("Noi Bai International Airport", "HAN", "Hanoi", "Hanoi", "Vietnam");
        airportSessionBean.createNewAirport(noiBaiInternationalAirport);
        
        Airport ninoyAquinoInternationalAirport = new Airport("Ninoy Aquino International Airport", "MNL", "Manila", "Manila", "Philippines");
        airportSessionBean.createNewAirport(ninoyAquinoInternationalAirport);
        
        Airport hongKongInternationalAirport = new Airport("Hong Kong International Airport", "HKG", "Hong Kong", "Hong Kong", "Hong Kong");
        airportSessionBean.createNewAirport(hongKongInternationalAirport);
        
        Airport taiwanTaoyuanInternationalAirport = new Airport("Taiwan Taoyuan International Airport", "TPE", "Taoyuan", "Taoyuan", "Taiwan");
        airportSessionBean.createNewAirport(taiwanTaoyuanInternationalAirport);
        
        Airport indiraGandhiInternationalAirport = new Airport("Indira Gandhi International Airport", "DEL", "Delhi", "Delhi", "India");
        airportSessionBean.createNewAirport(indiraGandhiInternationalAirport);
        
        Airport beijingCapitalInternationalAirport = new Airport("Beijing Capital International Airport", "PEK", "Beijing", "Beijing", "China");
        airportSessionBean.createNewAirport(beijingCapitalInternationalAirport);
        
        Airport incheonInternationalAirport = new Airport("Incheon International Airport", "ICN", "Incheon", "Incheon", "South Korea");
        airportSessionBean.createNewAirport(incheonInternationalAirport);
        
        Airport hamadInternationalAirport = new Airport("Hamad International Airport", "DOH", "Doha", "Doha", "Qatar");
        airportSessionBean.createNewAirport(hamadInternationalAirport);
        
        Airport naritaInternationalAirport = new Airport("Narita International Airport", "NRT", "Narita", "Chiba", "Japan");
        airportSessionBean.createNewAirport(naritaInternationalAirport);
        
        Airport canberraAirport = new Airport("Canberra Airport", "CBR", "Canberra", "Canberra", "Australia");
        airportSessionBean.createNewAirport(canberraAirport);

    }
}
