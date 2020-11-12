/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.FlightSchedulePlanType;

/**
 *
 * @author GraceLi
 */
@Entity
public class FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightSchedulePlanId;
    @Enumerated(EnumType.STRING)
    private FlightSchedulePlanType flightSchedulePlanType;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Integer nDays;
    private Boolean enableFlight;
    
    @OneToOne
    private FlightSchedulePlan orginalFlightSchedulePlan;
    
    @OneToOne(mappedBy = "orginalFlightSchedulePlan")
    private FlightSchedulePlan complementaryReturnFlightSchedulePlan;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Flight flight;
    
    @OneToMany(mappedBy = "flightSchedulePlan")
    @JoinColumn(nullable = false)
    private List<FlightSchedule> flightSchedules;
    
    @OneToMany
    private List<Fare> fares;

    public FlightSchedulePlan() {
        this.fares = new ArrayList<>();
        this.flightSchedules = new ArrayList<>();
    }

    public FlightSchedulePlan(FlightSchedulePlanType flightSchedulePlanType) {
        this();
        this.flightSchedulePlanType = flightSchedulePlanType;
    }

    public FlightSchedulePlan(FlightSchedulePlanType flightSchedulePlanType, Flight flight) {
        this();
        this.flightSchedulePlanType = flightSchedulePlanType;
        this.flight = flight;
    }
    
    
    

    public Long getFlightSchedulePlanId() {
        return flightSchedulePlanId;
    }

    public void setFlightSchedulePlanId(Long flightSchedulePlanId) {
        this.flightSchedulePlanId = flightSchedulePlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightSchedulePlanId != null ? flightSchedulePlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightSchedulePlanId fields are not set
        if (!(object instanceof FlightSchedulePlan)) {
            return false;
        }
        FlightSchedulePlan other = (FlightSchedulePlan) object;
        if ((this.flightSchedulePlanId == null && other.flightSchedulePlanId != null) || (this.flightSchedulePlanId != null && !this.flightSchedulePlanId.equals(other.flightSchedulePlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedulePlan[ id=" + flightSchedulePlanId + " ]";
    }

    public FlightSchedulePlanType getFlightSchedulePlanType() {
        return flightSchedulePlanType;
    }

    public void setFlightSchedulePlanType(FlightSchedulePlanType flightSchedulePlanType) {
        this.flightSchedulePlanType = flightSchedulePlanType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightSchedule> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    public Integer getnDays() {
        return nDays;
    }

    public void setnDays(Integer nDays) {
        this.nDays = nDays;
    }

    public Boolean getEnableFlight() {
        return enableFlight;
    }

    public void setEnableFlight(Boolean enableFlight) {
        this.enableFlight = enableFlight;
    }

    public FlightSchedulePlan getOrginalFlightSchedulePlan() {
        return orginalFlightSchedulePlan;
    }

    public void setOrginalFlightSchedulePlan(FlightSchedulePlan orginalFlightSchedulePlan) {
        this.orginalFlightSchedulePlan = orginalFlightSchedulePlan;
    }

    public FlightSchedulePlan getComplementaryReturnFlightSchedulePlan() {
        return complementaryReturnFlightSchedulePlan;
    }

    public void setComplementaryReturnFlightSchedulePlan(FlightSchedulePlan complementaryReturnFlightSchedulePlan) {
        this.complementaryReturnFlightSchedulePlan = complementaryReturnFlightSchedulePlan;
    }
    
}
