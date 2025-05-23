package com.mydigitalbank.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "tb_update_info")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UpdateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long infoId;

    @NotBlank(message = "Image URL cannot be blank.")
    @Size(max = 255, message = "Image URL must not exceed 255 characters.")
    @Column(length = 255)
    private String imageUrl;

    @NotBlank(message = "Update description cannot be blank.")
    @Column(columnDefinition = "TEXT")
    private String updateDescription;
}
