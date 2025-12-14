package com.library.catalog.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publishers", indexes = {
    @Index(name = "idx_publisher_name", columnList = "publisher_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherEntity extends BaseEntity {

    @Column(name = "publisher_name", nullable = false, length = 100)
    private String publisherName;

    @Column(length = 255)
    private String address;
}
