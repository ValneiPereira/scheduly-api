package com.scheduly.api.infrastructure.persistence.professional;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProfessionalServiceId implements Serializable {
    private Long professionalId;
    private Long departmentId;
}
