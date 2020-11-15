/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.SeatStatus;

/**
 *
 * @author GraceLi
 */
@Entity
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(nullable = false)
    private Integer seatNumber;
    @Column(nullable = false)
    private String rowAlphabet;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    public Seat() {
    }

    public Seat(Integer seatNumber, String rowAlphabet) {
        this.seatNumber = seatNumber;
        this.rowAlphabet = rowAlphabet;
    }
    
    

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seatId != null ? seatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the seatId fields are not set
        if (!(object instanceof Seat)) {
            return false;
        }
        Seat other = (Seat) object;
        if ((this.seatId == null && other.seatId != null) || (this.seatId != null && !this.seatId.equals(other.seatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Seat[ id=" + seatId + " ]";
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getRowAlphabet() {
        return rowAlphabet;
    }

    public void setRowAlphabet(String rowAlphabet) {
        this.rowAlphabet = rowAlphabet;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
    
}
