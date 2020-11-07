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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author GraceLi
 */
@Entity
public class AircraftConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftConfigurationId;
    private Integer numberOfCabinClasses;
    private Integer totalMaximumSeatCapacity;
    
    @OneToMany(mappedBy = "aircraftConfiguration")
    private List<Flight> flights;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AircraftType aircraftType;
    
    @OneToMany(mappedBy = "aircraftConfiguration")
    @JoinColumn(nullable = false)
    private List<CabinClassConfiguration> cabinClassConfigurations;

    public AircraftConfiguration() {
    }
    
    

    public Long getAircraftConfigurationId() {
        return aircraftConfigurationId;
    }

    public void setAircraftConfigurationId(Long aircraftConfigurationId) {
        this.aircraftConfigurationId = aircraftConfigurationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aircraftConfigurationId != null ? aircraftConfigurationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the aircraftConfigurationId fields are not set
        if (!(object instanceof AircraftConfiguration)) {
            return false;
        }
        AircraftConfiguration other = (AircraftConfiguration) object;
        if ((this.aircraftConfigurationId == null && other.aircraftConfigurationId != null) || (this.aircraftConfigurationId != null && !this.aircraftConfigurationId.equals(other.aircraftConfigurationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AircraftConfiguration[ id=" + aircraftConfigurationId + " ]";
    }

    public Integer getNumberOfCabinClasses() {
        return numberOfCabinClasses;
    }

    public void setNumberOfCabinClasses(Integer numberOfCabinClasses) {
        this.numberOfCabinClasses = numberOfCabinClasses;
    }

    public Integer getTotalMaximumSeatCapacity() {
        return totalMaximumSeatCapacity;
    }

    public void setTotalMaximumSeatCapacity(Integer totalMaximumSeatCapacity) {
        this.totalMaximumSeatCapacity = totalMaximumSeatCapacity;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public List<CabinClassConfiguration> getCabinClassConfigurations() {
        return cabinClassConfigurations;
    }

    public void setCabinClassConfigurations(List<CabinClassConfiguration> cabinClassConfigurations) {
        this.cabinClassConfigurations = cabinClassConfigurations;
    }
    
}
