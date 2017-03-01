package com.pgs.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wkloc on 2017-02-27.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users createdBy;

    private Date createdOn;

    private Date deletedOn;

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }
}
