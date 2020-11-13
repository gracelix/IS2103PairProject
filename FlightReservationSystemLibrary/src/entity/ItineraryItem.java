/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ziyue
 */
@Entity
public class ItineraryItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryItemId;
    @Column(length = 32, nullable = false)
    private String odDateTime;
    @Column(length = 32, nullable = false)
    private String odCode;
    @Column(length = 32, nullable = false)
    private String cabinClass;
    @Column(length = 32, nullable = false)
    private String seatNumber;
    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal fareAmount;
    @Column(length = 64, nullable = false)
    private String passengerName;
    @Column(length = 32, nullable = false)
    private String fareBasisCode;
    
    @ManyToOne
    private FlightSchedule flightSchedule;

    public ItineraryItem() {
    }

    public ItineraryItem(String odDateTime, String odCode, String cabinClass, String seatNumber, BigDecimal fareAmount, String passengerName, String fareBasisCode) {
        this.odDateTime = odDateTime;
        this.odCode = odCode;
        this.cabinClass = cabinClass;
        this.seatNumber = seatNumber;
        this.fareAmount = fareAmount;
        this.passengerName = passengerName;
        this.fareBasisCode = fareBasisCode;
    }
    

    public Long getItineraryItemId() {
        return itineraryItemId;
    }

    public void setItineraryItemId(Long itineraryItemId) {
        this.itineraryItemId = itineraryItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itineraryItemId != null ? itineraryItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the itineraryItemId fields are not set
        if (!(object instanceof ItineraryItem)) {
            return false;
        }
        ItineraryItem other = (ItineraryItem) object;
        if ((this.itineraryItemId == null && other.itineraryItemId != null) || (this.itineraryItemId != null && !this.itineraryItemId.equals(other.itineraryItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightReservationItinerary[ id=" + itineraryItemId + " ]";
    }

    public String getOdDateTime() {
        return odDateTime;
    }

    public void setOdDateTime(String odDateTime) {
        this.odDateTime = odDateTime;
    }

    public String getOdCode() {
        return odCode;
    }

    public void setOdCode(String odCode) {
        this.odCode = odCode;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getFareBasisCode() {
        return fareBasisCode;
    }

    public void setFareBasisCode(String fareBasisCode) {
        this.fareBasisCode = fareBasisCode;
    }
    
}
