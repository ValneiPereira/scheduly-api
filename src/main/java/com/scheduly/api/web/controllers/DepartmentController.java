package com.scheduly.api.web.controllers;

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
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final CreateDepartmentUseCase createDepartmentUseCase;
    private final GetDepartmentUseCase getDepartmentUseCase;
    private final ListDepartmentsUseCase listDepartmentsUseCase;
    private final UpdateDepartmentUseCase updateDepartmentUseCase;
    private final DeleteDepartmentUseCase deleteDepartmentUseCase;
    private final DepartmentMapper mapper;

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest request) {
        Department domain = mapper.toDomain(request);
        Department created = createDepartmentUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> get(@PathVariable Long id) {
        Department department = getDepartmentUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(department));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> list(@RequestParam(required = false) String category) {
        List<Department> departments = listDepartmentsUseCase.execute(category);
        return ResponseEntity.ok(departments.stream().map(mapper::toResponse).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id, @RequestBody DepartmentUpdate request) {
        Department domain = mapper.toDomain(request, id);
        Department updated = updateDepartmentUseCase.execute(id, domain);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteDepartmentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
