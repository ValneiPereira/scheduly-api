package com.scheduly.api.web.controllers;

import com.scheduly.api.DepartmentsApi;
import com.scheduly.api.application.department.GetDepartmentUseCase;
import com.scheduly.api.application.department.ListDepartmentsUseCase;
import com.scheduly.api.domain.department.Department;
import com.scheduly.api.web.dtos.DepartmentResponse;
import com.scheduly.api.web.mappers.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de departamentos
 */
@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentsApi {

    private final GetDepartmentUseCase getDepartmentUseCase;
    private final ListDepartmentsUseCase listDepartmentsUseCase;
    private final DepartmentMapper mapper;

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
}
