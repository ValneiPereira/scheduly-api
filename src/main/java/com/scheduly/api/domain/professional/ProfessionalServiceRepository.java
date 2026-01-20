package com.scheduly.api.domain.professional;

import java.util.List;

public interface ProfessionalServiceRepository {
    List<ProfessionalService> findByProfessionalId(Long professionalId);
    java.util.Optional<ProfessionalService> findByProfessionalIdAndDepartmentId(Long professionalId, Long departmentId);
    ProfessionalService save(ProfessionalService professionalService);
    void deleteByProfessionalIdAndDepartmentId(Long professionalId, Long departmentId);
}
