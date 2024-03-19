package com.ilyanavoid.secondpractice.controllers;

import com.ilyanavoid.secondpractice.models.EmployeeDto;
import com.ilyanavoid.secondpractice.models.Employee;
import com.ilyanavoid.secondpractice.models.EmployeeListDto;
import com.ilyanavoid.secondpractice.models.ResponseDto;
import com.ilyanavoid.secondpractice.repositories.EmployeeRepository;
import com.ilyanavoid.secondpractice.services.EmployeesDataManagementService;
import com.ilyanavoid.secondpractice.services.UtilsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EmployeesDataManagementController {
    @Autowired
    EmployeesDataManagementService employeesDataManagementService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UtilsService utilsService;

    @PostMapping("api/add")
    public ResponseEntity addEmployee(@RequestBody EmployeeDto employee) {
        UUID id = null;
        if (employee.getId() != null && !utilsService.isUUID(employee.getId())) {
            ResponseDto response = new ResponseDto("FAILED (ID is not UUID)", id);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            id = employeesDataManagementService.createEmployee(employee.getId(), employee.getName(), employee.getPosition());
            ResponseDto response = new ResponseDto("SUCCESS", id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("api/get")
    public ResponseEntity getAllEmployees(@RequestParam(required = false) String name, @RequestParam(required = false) String position) {
        try {
            List<Employee> list = employeesDataManagementService.getAllEmployees(name, position);
            EmployeeListDto response = new EmployeeListDto(list, list.size());
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("api/delete")
    public ResponseEntity deleteEmployee(@RequestParam(required = true) String id) {
        UUID deleted = null;
        if (!utilsService.isUUID(id)) {
            return ResponseEntity.badRequest().body("Incorrect id!");
        }

        String authorizationHeader = request.getHeader("Authorization");
        String bearerToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            bearerToken = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        if(!utilsService.isUUID(bearerToken)) {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        Employee requestSender = employeeRepository.getById(UUID.fromString(bearerToken));
        if (requestSender.getPosition().equals("Admin")) {
            try {
                deleted = employeesDataManagementService.deleteEmployee(id);
                ResponseDto response = new ResponseDto("DELETED", deleted);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                ResponseDto response = new ResponseDto("FAILED", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
            }
        }
        else {
            try {
                deleted = employeesDataManagementService.deleteEmployee(bearerToken);
                ResponseDto response = new ResponseDto("DELETED", deleted);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                ResponseDto response = new ResponseDto("FAILED", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
            }
        }
    }

    @GetMapping("/api/getone")
    public ResponseEntity getOneEmployee(@RequestParam(required = true) String id) {
        if (!utilsService.isUUID(id)) {
            return ResponseEntity.badRequest().body("Incorrect id!");
        }
        String authorizationHeader = request.getHeader("Authorization");
        String bearerToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            bearerToken = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        if(!utilsService.isUUID(bearerToken)) {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        Optional<Employee> requestSenderOpt = employeeRepository.findById(UUID.fromString(bearerToken));
        if (requestSenderOpt.isPresent()) {
            Employee requestSender = requestSenderOpt.get();
            if (requestSender.getPosition().equals("Admin")) {
                try {
                    Employee target = employeesDataManagementService.getEmployee(id);
                    if (target != null) return ResponseEntity.ok(target);
                    else return ResponseEntity.notFound().build();
                }
                catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
                }
            } else {
                return ResponseEntity.ok(requestSender);
            }
        }
        else {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }
    }

    @PatchMapping("/api/update")
    public ResponseEntity updateEmployee(@RequestParam(required = true) String id, @RequestBody EmployeeDto data) {
        if (!utilsService.isUUID(id)) {
            return ResponseEntity.badRequest().body("Incorrect id!");
        }
        String authorizationHeader = request.getHeader("Authorization");
        String bearerToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            bearerToken = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        if(!utilsService.isUUID(bearerToken)) {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }

        Optional<Employee> requestSenderOpt = employeeRepository.findById(UUID.fromString(bearerToken));
        if (requestSenderOpt.isPresent()) {
            Employee requestSender = requestSenderOpt.get();
            if (requestSender.getPosition().equals("Admin")) {
                try {
                    UUID target = employeesDataManagementService.updateEmployee(id, data);
                    ResponseDto response = new ResponseDto("UPDATED", target);
                    if (target != null) return ResponseEntity.ok(response);
                    else return ResponseEntity.notFound().build();
                }
                catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
                }
            } else {
                UUID target = employeesDataManagementService.updateEmployee(bearerToken, data);
                ResponseDto response = new ResponseDto("UPDATED", target);
                if (target != null) return ResponseEntity.ok(response);
                else return ResponseEntity.notFound().build();
            }
        }
        else {
            return ResponseEntity.badRequest().body("Incorrect authorization header!");
        }
    }
}
