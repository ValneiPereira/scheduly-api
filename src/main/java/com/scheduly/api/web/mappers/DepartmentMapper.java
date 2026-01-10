package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.common.MoneyConverter;
import com.scheduly.api.domain.department.Department;
import com.scheduly.api.domain.department.DepartmentCategory;
import com.scheduly.api.domain.department.DepartmentSubcategory;
import com.scheduly.api.web.dtos.DepartmentRequest;
import com.scheduly.api.web.dtos.DepartmentResponse;
import com.scheduly.api.web.dtos.DepartmentUpdate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.scheduly.api.domain.common.MoneyConverter.toCents;

@Component
public class DepartmentMapper {

    public Department toDomain(DepartmentRequest request) {
        if (request == null) return null;

        DepartmentCategory category = DepartmentCategory.valueOf(request.category());
        DepartmentSubcategory subcategory = DepartmentSubcategory.valueOf(request.subcategory());

        if (!subcategory.belongsTo(category)) {
            throw new IllegalArgumentException("Subcategoria " + subcategory + " não pertence à categoria " + category);
        }

        return Department.builder()
                .name(request.name())
                .description(request.description())
                .category(category)
                .subcategory(subcategory)
                .duration(request.durationMinutes())
                .price(MoneyConverter.toDomain(BigDecimal.valueOf(request.priceCents())))
                .build();
    }

    public Department toDomain(DepartmentUpdate request, Long id) {
        if (request == null) return null;

        var builder = Department.builder()
                .id(id)
                .name(request.name())
                .description(request.description())
                .duration(request.durationMinutes())
                .price(request.priceCents() != null ? MoneyConverter.toDomain(BigDecimal.valueOf(request.priceCents())) : null);

        if (request.category() != null) {
            DepartmentCategory category = DepartmentCategory.valueOf(request.category());
            builder.category(category);


            if (request.subcategory() != null) {
                DepartmentSubcategory subcategory = DepartmentSubcategory.valueOf(request.subcategory());
                if (!subcategory.belongsTo(category)) {
                    throw new IllegalArgumentException("Subcategoria " + subcategory + " não pertence à categoria " + category);
                }
                builder.subcategory(subcategory);
            }
        } else if (request.subcategory() != null) {
            // Se categoria não veio, mas subcategoria veio
            builder.subcategory(DepartmentSubcategory.valueOf(request.subcategory()));
            // validação real será feita no merge do UseCase / entidade
        }
        return builder.build();
    }

    public DepartmentResponse toResponse(Department department) {
        if (department == null) return null;

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getCategory().name(),
                department.getSubcategory().name(),
                department.getDuration(),
                toCents(department.getPrice()).intValue(),
                department.getCreatedAt()
        );
    }
}
