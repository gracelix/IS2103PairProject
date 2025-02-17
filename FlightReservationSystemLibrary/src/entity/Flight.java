/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author GraceLi
 */
@Entity
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    @Column(unique = true, nullable = false)
    private String flightNumber;
    private Boolean enableFlight;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private FlightRoute flightRoute;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AircraftConfiguration aircraftConfiguration;
    
    @OneToOne
    private Flight originalFlight;
    
    @OneToOne(mappedBy = "originalFlight")
    private Flight complementaryReturnFlight;
    
    @OneToMany(mappedBy = "flight")
    private List<FlightSchedulePlan> flightSchedulePlans;

    public Flight() {
        this.flightSchedulePlans = new ArrayList<>();
    }

    public Flight(String flightNumber) {
        this();
        this.flightNumber = flightNumber;
    }   
    

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightId != null ? flightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightId fields are not set
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Flight[ id=" + flightId + " ]";
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public FlightRoute getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(FlightRoute flightRoute) {
        this.flightRoute = flightRoute;
    }

    public AircraftConfiguration getAircraftConfiguration() {
        return aircraftConfiguration;
    }

    public void setAircraftConfiguration(AircraftConfiguration aircraftConfiguration) {
        this.aircraftConfiguration = aircraftConfiguration;
    }

    public Boolean getEnableFlight() {
        return enableFlight;
    }

    public void setEnableFlight(Boolean enableFlight) {
        this.enableFlight = enableFlight;
    }

    public Flight getComplementaryReturnFlight() {
        return complementaryReturnFlight;
    }

    public void setComplementaryReturnFlight(Flight complementaryReturnFlight) {
        this.complementaryReturnFlight = complementaryReturnFlight;
    }

    public List<FlightSchedulePlan> getFlightSchedulePlans() {
        return flightSchedulePlans;
    }

    public void setFlightSchedulePlans(List<FlightSchedulePlan> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
    }

    public Flight getOriginalFlight() {
        return originalFlight;
    }

    public void setOriginalFlight(Flight originalFlight) {
        this.originalFlight = originalFlight;
    }
    
}
