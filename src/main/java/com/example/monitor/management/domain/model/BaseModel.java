package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseModel<T extends BaseModel<T>> {

    @Id
    @Size(min = 36, max = 36)
    protected String id;
    @Version
    @JsonIgnore
    private Integer version;
    @JsonIgnore
    @Column(name = "created_date")
    protected Date createdDate;
    @JsonIgnore
    @Column(name = "updated_date")
    protected Date updatedDate;
    @JsonIgnore
    @Column(name = "updated_by")
    protected String updatedBy;
    @JsonIgnore
    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "is_hided")
    @JsonProperty("is_hided")
    protected boolean isHided;
    @Column(name = "is_deleted")
    @JsonProperty("is_deleted")
    protected boolean isDeleted;

    public BaseModel() {
        this.id = UUID.randomUUID().toString();
        this.createdDate = new Date(System.currentTimeMillis());
        this.updatedDate = new Date(System.currentTimeMillis());
        this.isDeleted = false;
        this.isDeleted = false;
    }
    protected BaseModel(String id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date(System.currentTimeMillis());
    }

    private void checkIdentity(final BaseModel<?> entity) {
        if (entity.getId() == null) {
            throw new IllegalStateException("Identity missing in entity: " + entity);
        }
    }

    public boolean sameIdentityAs(final T that) {
        return this.equals(that);
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof BaseModel)) {
            return false;
        }
        final BaseModel<?> that = (BaseModel<?>) object;
        checkIdentity(this);
        checkIdentity(that);
        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
