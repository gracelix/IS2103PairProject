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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.CabinClassType;

/**
 *
 * @author GraceLi
 */
@Entity
public class CabinClassConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabinClassConfigurationId;
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClassType;
    @Column(nullable = false)
    private Integer numberOfAisles;
    @Column(nullable = false)
    private Integer numberOfRows;
    @Column(nullable = false)
    private Integer numberOfSeatsAbreast;
    @Column(nullable = false)
    private String seatingConfiguration;
    @Column(nullable = false)
    private Integer cabinMaximumSeatCapacity;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AircraftConfiguration aircraftConfiguration;
    
    @OneToMany(mappedBy = "cabinClassConfiguration")
    private List<Fare> fares;
    @OneToMany(mappedBy = "cabinClass")
    private List<SeatInventory> seatInventories;

    public CabinClassConfiguration() {
        this.fares = new ArrayList<>();
        this.seatInventories = new ArrayList<>();
    }

    public CabinClassConfiguration(CabinClassType cabinClassType, Integer numberOfAisles, Integer numberOfRows, Integer numberOfSeatsAbreast, String seatingConfiguration, Integer cabinMaximumSeatCapacity) {
        this();
        this.cabinClassType = cabinClassType;
        this.numberOfAisles = numberOfAisles;
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsAbreast = numberOfSeatsAbreast;
        this.seatingConfiguration = seatingConfiguration;
        this.cabinMaximumSeatCapacity = cabinMaximumSeatCapacity;
    }
    
    

    public Long getCabinClassConfigurationId() {
        return cabinClassConfigurationId;
    }

    public void setCabinClassConfigurationId(Long cabinClassConfigurationId) {
        this.cabinClassConfigurationId = cabinClassConfigurationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cabinClassConfigurationId != null ? cabinClassConfigurationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cabinClassConfigurationId fields are not set
        if (!(object instanceof CabinClassConfiguration)) {
            return false;
        }
        CabinClassConfiguration other = (CabinClassConfiguration) object;
        if ((this.cabinClassConfigurationId == null && other.cabinClassConfigurationId != null) || (this.cabinClassConfigurationId != null && !this.cabinClassConfigurationId.equals(other.cabinClassConfigurationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CabinClassConfiguration[ id=" + cabinClassConfigurationId + " ]";
    }

    public CabinClassType getCabinClassType() {
        return cabinClassType;
    }

    public void setCabinClassType(CabinClassType cabinClassType) {
        this.cabinClassType = cabinClassType;
    }

    public Integer getNumberOfAisles() {
        return numberOfAisles;
    }

    public void setNumberOfAisles(Integer numberOfAisles) {
        this.numberOfAisles = numberOfAisles;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfSeatsAbreast() {
        return numberOfSeatsAbreast;
    }

    public void setNumberOfSeatsAbreast(Integer numberOfSeatsAbreast) {
        this.numberOfSeatsAbreast = numberOfSeatsAbreast;
    }

    public String getSeatingConfiguration() {
        return seatingConfiguration;
    }

    public void setSeatingConfiguration(String seatingConfiguration) {
        this.seatingConfiguration = seatingConfiguration;
    }

    public Integer getCabinMaximumSeatCapacity() {
        return cabinMaximumSeatCapacity;
    }

    public void setCabinMaximumSeatCapacity(Integer cabinMaximumSeatCapacity) {
        this.cabinMaximumSeatCapacity = cabinMaximumSeatCapacity;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    public List<SeatInventory> getSeatInventories() {
        return seatInventories;
    }

    public void setSeatInventories(List<SeatInventory> seatInventories) {
        this.seatInventories = seatInventories;
    }
    
    public AircraftConfiguration getAircraftConfiguration() {
        return aircraftConfiguration;
    }

    public void setAircraftConfiguration(AircraftConfiguration aircraftConfiguration) {
        this.aircraftConfiguration = aircraftConfiguration;
    }
    
}
