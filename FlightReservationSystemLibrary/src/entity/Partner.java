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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import util.enumeration.PartnerAccessRights;

/**
 *
 * @author Ziyue
 */
@Entity
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
    @Column(length = 32, nullable = false)
    private String name;
    @Column(length = 32, nullable = false)
    private String userName;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartnerAccessRights partnerAccessRights;
    
    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    private List<Transaction> partnerTransactions;

    public Partner() {
        partnerTransactions = new ArrayList<>();
    }

    public Partner(String name, String userName, String password, PartnerAccessRights partnerAccessRight) {
        this();
        
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.partnerAccessRights = partnerAccessRight;
    }

    
    
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerId != null ? partnerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partnerId fields are not set
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.partnerId == null && other.partnerId != null) || (this.partnerId != null && !this.partnerId.equals(other.partnerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partner[ id=" + partnerId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PartnerAccessRights getPartnerAccessRights() {
        return partnerAccessRights;
    }

    public void setPartnerAccessRights(PartnerAccessRights partnerAccessRights) {
        this.partnerAccessRights = partnerAccessRights;
    }

    public List<Transaction> getPartnerTransactions() {
        return partnerTransactions;
    }

    public void setPartnerTransactions(List<Transaction> partnerTransactions) {
        this.partnerTransactions = partnerTransactions;
    }
    
}
