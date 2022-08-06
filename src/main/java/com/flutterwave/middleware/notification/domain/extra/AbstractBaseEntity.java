package com.flutterwave.middleware.notification.domain.extra;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@ToString
@Getter
@Setter
@MappedSuperclass
public class AbstractBaseEntity implements Serializable, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    private Long version = 0L;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy = "SYSTEM";

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy = "SYSTEM";


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return version == that.version && Objects.equals(id, that.id)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(createdBy, that.createdBy)
                && Objects.equals(lastModifiedDate, that.lastModifiedDate)
                && Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, createdDate, createdBy, lastModifiedDate, lastModifiedBy);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
