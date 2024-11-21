package com.uzay.securitygeneltekrarr.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    
    @CreatedDate
    private Date createdAt ;
    
    @LastModifiedDate
    private Date lastmodifiedAt ;
    
    @CreatedBy
    private String createdBy ;
    
    
    @LastModifiedBy
    private String lastmodifiedBy;
    
    
    
    
    
    
}
