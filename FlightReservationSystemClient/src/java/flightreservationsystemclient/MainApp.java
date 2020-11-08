/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRights;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
public class MainApp {
    private AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private Employee currentEmployee;
    private Integer response;
    
    public MainApp() {
    }

    public MainApp(AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        response = 0;

        while (true) {
            System.out.println("*** Welcome to Flight Reservation System :: Management ***\n");

            if(currentEmployee != null) {
                System.out.println("You are login as " + currentEmployee.getName() + "\n");

            } else {
                System.out.println("1: Login");
                System.out.println("2: Exit\n");

                response = 0;
                while (response < 1 || response > 2) {
                    System.out.print("> ");
                    response = sc.nextInt();

                    if (response == 1) {
                        try {
                            doLogin();
                            System.out.println("You have login as " + currentEmployee.getName() + "\n");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } else if (response == 2) {
                        break;
                    }
                }
            }

            if (currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.FLEET_MANAGER)) {
                doFleetManagerMenu();
                if (response == 4) {
                    break;
                }
            }


        }


    }

    public void doFleetManagerMenu() {
        Scanner sc = new Scanner(System.in);
        response = 0;

        System.out.println("1: Create aircraft configuration");
        System.out.println("2: View all aircraft configuration");
        System.out.println("3: View aircraft configuration details");
        System.out.println("4: Exit\n");

        response = 0;
        while (response < 1 || response > 4) {
            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                System.out.println("creating aircraft config");
            } else if (response == 2) {
                System.out.println("viewing all aircraft config");
            } else if (response == 3) {
                System.out.println("viewing aircraft config details");
            } else if (response == 4) {
                break;
            }
        }
    }

    public void doLogin() throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);

        System.out.println("*** Flight Reservation System Management :: Login ***\n");
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentEmployee = employeeSessionBeanRemote.login(username, password);
        } else {
            throw new InvalidLoginCredentialException("One or more login credentials are missing.");
        }
    }
    
    public void doCreateAircraftConfiguration() {
        Scanner sc = new Scanner(System.in);
    }
}
