package com.mydigitalbank.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "tb_service_item")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long itemId;

    @NotBlank(message = "Image URL cannot be blank.")
    @Size(max = 255, message = "Image URL must not exceed 255 characters.")
    @Column(length = 255)
    private String imageUrl;

    @NotBlank(message = "Service description cannot be blank.")
    @Column(columnDefinition = "TEXT")
    private String serviceDescription;
}
