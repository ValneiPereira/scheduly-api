package com.scheduly.api.infrastructure.persistence.department;

import com.scheduly.api.domain.department.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentEntityMapper {

    public Department toDomain(DepartmentEntity entity) {
        if (entity == null) {
            return null;
        }
        return Department.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .subcategory(entity.getSubcategory())
                .price(entity.getPrice())
                .duration(entity.getDuration())
                .requirements(entity.getRequirements())
                .materials(entity.getMaterials())
                .requiresSpecialist(entity.getRequiresSpecialist())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public DepartmentEntity toEntity(Department domain) {
        if (domain == null) {
            return null;
        }
        return DepartmentEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .category(domain.getCategory())
                .subcategory(domain.getSubcategory())
                .price(domain.getPrice())
                .duration(domain.getDuration())
                .requirements(domain.getRequirements())
                .materials(domain.getMaterials())
                .requiresSpecialist(domain.getRequiresSpecialist())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
