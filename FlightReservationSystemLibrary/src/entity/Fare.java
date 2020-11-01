/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author GraceLi
 */
@Entity
public class Fare implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fareId;
    private String fareBasisCode;
    private BigDecimal fareAmount;
    @ManyToOne
    private CabinClassConfiguration cabinClassConfiguration;

    public Fare() {
    }
    
    

    public Long getFareId() {
        return fareId;
    }

    public void setFareId(Long fareId) {
        this.fareId = fareId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fareId != null ? fareId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the fareId fields are not set
        if (!(object instanceof Fare)) {
            return false;
        }
        Fare other = (Fare) object;
        if ((this.fareId == null && other.fareId != null) || (this.fareId != null && !this.fareId.equals(other.fareId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fare[ id=" + fareId + " ]";
    }
    
    
    public String getFareBasisCode() {
        return fareBasisCode;
    }

    public void setFareBasisCode(String fareBasisCode) {
        this.fareBasisCode = fareBasisCode;
    }

    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

    public CabinClassConfiguration getCabinClassConfiguration() {
        return cabinClassConfiguration;
    }

    public void setCabinClassConfiguration(CabinClassConfiguration cabinClassConfiguration) {
        this.cabinClassConfiguration = cabinClassConfiguration;
    }

}
