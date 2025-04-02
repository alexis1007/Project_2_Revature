package org.example.controller;

import java.util.List;
import java.util.Optional;

import org.example.Service.LoanTypeService;
import org.example.model.LoanType;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/type")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @Autowired
    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @GetMapping
    public  ResponseEntity<?> getAllLoanTypes(HttpServletRequest request){
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 ) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<LoanType> loanTypes= loanTypeService.findAllLoanTypes();
        return ResponseEntity.ok(loanTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanType> getLoanTypeById(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }


        Optional<LoanType> loanType = loanTypeService.findLoanTypeById(id);
        return loanType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<LoanType> createLoanType(@RequestBody LoanType loanType){
        LoanType createLoanType = loanTypeService.createLoanType(loanType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createLoanType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanType> updateLoanType(@PathVariable Long id, @RequestBody LoanType loanType, HttpServletRequest request){
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<LoanType> updateLoanType = loanTypeService.updateLoanType(id, loanType);
        return updateLoanType.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id, HttpServletRequest request){
        User sessionUser = (User) request.getAttribute("user");


        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean isDeleted = loanTypeService.deleteLoanType(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
