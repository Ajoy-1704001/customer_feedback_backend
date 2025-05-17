package com.deb.customer_feedback_backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Root {
	public static final String AUDIT_SEPERATOR = "_@_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdAt;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime lastModifiedAt;

    public Long getCreatedByUserId() {
        try {
            return Long.parseLong(createdBy.split(AUDIT_SEPERATOR)[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public String getCreatedByUserPrivilege() {
        try {
            return createdBy.split(AUDIT_SEPERATOR)[1];
        } catch (Exception e) {
            return null;
        }
    }

    public Long getLastModifiedByUserId() {
        try {
            return Long.parseLong(lastModifiedBy.split(AUDIT_SEPERATOR)[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastModifiedByUserPrivilege() {
        try {
            return lastModifiedBy.split(AUDIT_SEPERATOR)[1];
        } catch (Exception e) {
            return null;
        }
    }
}
