package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import com.scheduly.api.infrastructure.persistence.department.DepartmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfessionalServiceRepositoryImpl implements ProfessionalServiceRepository {

    private final ProfessionalServiceJpaRepository professionalServiceJpaRepository;
    private final ProfessionalJpaRepository professionalJpaRepository;
    private final DepartmentJpaRepository departmentJpaRepository;
    private final ProfessionalServiceEntityMapper mapper;

    @Override
    public List<ProfessionalService> findByProfessionalId(Long professionalId) {
        return professionalServiceJpaRepository.findByProfessional_Id(professionalId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public ProfessionalService save(ProfessionalService professionalService) {
        var professionalRef = professionalJpaRepository.getReferenceById(professionalService.getProfessionalId());
        var departmentRef = departmentJpaRepository.getReferenceById(professionalService.getDepartmentId());
        var entity = ProfessionalServiceEntity.builder()
                .id(new ProfessionalServiceId(
                        professionalService.getProfessionalId(),
                        professionalService.getDepartmentId()
                ))
                .professional(professionalRef)
                .department(departmentRef)
                .priceCents(professionalService.getPriceCents())
                .durationMinutes(professionalService.getDurationMinutes())
                .build();

        return mapper.toDomain(professionalServiceJpaRepository.save(entity));
    }

    @Override
    public void deleteByProfessionalIdAndDepartmentId(Long professionalId, Long departmentId) {
        professionalServiceJpaRepository.deleteByProfessional_IdAndDepartment_Id(professionalId, departmentId);
    }
}
