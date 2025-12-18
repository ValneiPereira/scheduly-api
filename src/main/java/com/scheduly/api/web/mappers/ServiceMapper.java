package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.common.MoneyConverter;
import com.scheduly.api.domain.service.Service;
import com.scheduly.api.domain.service.ServiceCategory;
import com.scheduly.api.domain.service.ServiceSubcategory;
import com.scheduly.api.web.dtos.ServiceRequest;
import com.scheduly.api.web.dtos.ServiceResponse;
import com.scheduly.api.web.dtos.ServiceUpdate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.scheduly.api.domain.common.MoneyConverter.toCents;

@Component
public class ServiceMapper {

    public Service toDomain(ServiceRequest request) {
        if (request == null) return null;

        ServiceCategory category = ServiceCategory.valueOf(request.category());
        ServiceSubcategory subcategory = ServiceSubcategory.valueOf(request.subcategory());

        if (!subcategory.belongsTo(category)) {
            throw new IllegalArgumentException("Subcategoria " + subcategory + " não pertence à categoria " + category);
        }

        return Service.builder()
                .name(request.name())
                .description(request.description())
                .category(category)
                .subcategory(subcategory)
                .duration(request.durationMinutes())
                .price(MoneyConverter.toDomain(BigDecimal.valueOf(request.priceCents())))
                .build();
    }

    public Service toDomain(ServiceUpdate request, Long id) {
        if (request == null) return null;

        var builder = Service.builder()
                .id(id)
                .name(request.name())
                .description(request.description())
                .duration(request.durationMinutes())
                .price(request.priceCents() != null ? MoneyConverter.toDomain(BigDecimal.valueOf(request.priceCents())) : null);

        if (request.category() != null) {
            ServiceCategory category = ServiceCategory.valueOf(request.category());
            builder.category(category);


            if (request.subcategory() != null) {
                ServiceSubcategory subcategory = ServiceSubcategory.valueOf(request.subcategory());
                if (!subcategory.belongsTo(category)) {
                    throw new IllegalArgumentException("Subcategoria " + subcategory + " não pertence à categoria " + category);
                }
                builder.subcategory(subcategory);
            }
        } else if (request.subcategory() != null) {
            // Se categoria não veio, mas subcategoria veio
            builder.subcategory(ServiceSubcategory.valueOf(request.subcategory()));
            // validação real será feita no merge do UseCase / entidade
        }
        return builder.build();
    }

    public ServiceResponse toResponse(Service service) {
        if (service == null) return null;

        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getCategory().name(),
                service.getSubcategory().name(),
                service.getDuration(),
                toCents(service.getPrice()).intValue(),
                service.getCreatedAt()
        );
    }
}
