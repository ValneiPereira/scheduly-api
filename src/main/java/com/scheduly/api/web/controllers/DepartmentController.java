package com.scheduly.api.web.controllers;

import com.scheduly.api.DepartmentsApi;
import com.scheduly.api.application.department.*;
import com.scheduly.api.domain.department.Department;
import com.scheduly.api.web.dtos.DepartmentRequest;
import com.scheduly.api.web.dtos.DepartmentResponse;
import com.scheduly.api.web.dtos.DepartmentUpdate;
import com.scheduly.api.web.mappers.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de departamentos
 */
@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentsApi {

    private final CreateDepartmentUseCase createDepartmentUseCase;
    private final GetDepartmentUseCase getDepartmentUseCase;
    private final ListDepartmentsUseCase listDepartmentsUseCase;
    private final UpdateDepartmentUseCase updateDepartmentUseCase;
    private final DeleteDepartmentUseCase deleteDepartmentUseCase;
    private final DepartmentMapper mapper;

    @Override
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest request) {
        Department domain = mapper.toDomain(request);
        Department created = createDepartmentUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @Override
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable Long id) {
        Department department = getDepartmentUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(department));
    }

    @Override
    public ResponseEntity<List<DepartmentResponse>> listDepartments(@RequestParam(required = false) String category) {
        List<Department> departments = listDepartmentsUseCase.execute(category);
        return ResponseEntity.ok(departments.stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id, @RequestBody DepartmentUpdate request) {
        Department domain = mapper.toDomain(request, id);
        Department updated = updateDepartmentUseCase.execute(id, domain);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        deleteDepartmentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
