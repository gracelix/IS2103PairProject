/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author GraceLi
 */
@Entity
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;
    private String airportName;
    private String iataCode;
    private String city;
    private String countryState;
    private String country;
    
    @OneToMany(mappedBy = "originAirport")
    private List<FlightRoute> originFlightRoutes;
    @OneToMany(mappedBy = "destinationAirport")
    private List<FlightRoute> destinationFlightRoutes;

    
    public Airport() {
    }

    public Airport(String airportName, String iataCode, String city, String countryState, String country) {
        this.airportName = airportName;
        this.iataCode = iataCode;
        this.city = city;
        this.countryState = countryState;
        this.country = country;
    }
    
    

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airportId != null ? airportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the airportId fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.airportId == null && other.airportId != null) || (this.airportId != null && !this.airportId.equals(other.airportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Airport[ id=" + airportId + " ]";
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryState() {
        return countryState;
    }

    public void setCountryState(String countryState) {
        this.countryState = countryState;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<FlightRoute> getOriginFlightRoutes() {
        return originFlightRoutes;
    }

    public void setOriginFlightRoutes(List<FlightRoute> originFlightRoutes) {
        this.originFlightRoutes = originFlightRoutes;
    }

    public List<FlightRoute> getDestinationFlightRoutes() {
        return destinationFlightRoutes;
    }

    public void setDestinationFlightRoutes(List<FlightRoute> destinationFlightRoutes) {
        this.destinationFlightRoutes = destinationFlightRoutes;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    
}
