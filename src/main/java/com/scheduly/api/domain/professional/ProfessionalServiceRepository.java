package com.scheduly.api.domain.professional;

import java.util.List;

public interface ProfessionalServiceRepository {
    List<ProfessionalService> findByProfessionalId(Long professionalId);
    ProfessionalService save(ProfessionalService professionalService);
    void deleteByProfessionalIdAndDepartmentId(Long professionalId, Long departmentId);
}
