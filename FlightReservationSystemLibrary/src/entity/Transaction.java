/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Ziyue
 */
@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(length = 32, nullable = false)
    private String passengerFirstName;
    @Column(length = 32, nullable = false)
    private String passengerLastName;
    @Column(length = 32, nullable = false)
    private Long passportNumber;
    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal totalPrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Partner partner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Customer customer;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<ItineraryItem> itineraryItems;

    public Transaction() {
        itineraryItems = new ArrayList<>();
    }

    public Transaction(String passengerFirstName, String passengerLastName, Long passportNumber, BigDecimal totalPrice, Partner partner) {
        this();
        
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passportNumber = passportNumber;
        this.totalPrice = totalPrice;
        this.partner = partner;
        this.customer = null;
    }

    public Transaction(String passengerFirstName, String passengerLastName, Long passportNumber, BigDecimal totalPrice, Customer customer) {
        this();
        
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passportNumber = passportNumber;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.partner = null;
    }    

    public Transaction(String passengerFirstName, String passengerLastName, Long passportNumber, BigDecimal totalPrice) {
        this();
        
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passportNumber = passportNumber;
        this.totalPrice = totalPrice;
        this.partner = null;
        this.customer = null;
    }

    public Transaction(String passengerFirstName, String passengerLastName, Long passportNumber) {
        this();
        
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passportNumber = passportNumber;
    }
    
    
    

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the transactionId fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightReservationTransaction[ id=" + transactionId + " ]";
    }

    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }

    public Long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }
    
}
