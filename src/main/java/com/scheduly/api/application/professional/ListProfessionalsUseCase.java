package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListProfessionalsUseCase {

    private final ProfessionalRepository repository;

    @Transactional(readOnly = true)
    public List<Professional> execute() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Professional> execute(Long departmentId) {
        if (departmentId != null) {
            return repository.findByDepartmentId(departmentId);
        }
        return execute();
    }
}
