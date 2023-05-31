package com.example.lab7.controller;

import com.example.lab7.entities.Accione;
import com.example.lab7.entities.Pago;
import com.example.lab7.repository.AccioneRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping(value="/acciones", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class AccioneController {
    final AccioneRepository accioneRepository;

    public AccioneController(AccioneRepository accioneRepository) {
        this.accioneRepository = accioneRepository;
    }


    @PostMapping("/save")
    public ResponseEntity<HashMap<String, Object>> registrarPago(@RequestBody Accione accione){
        HashMap<String, Object> rspta = new HashMap<>();
        accioneRepository.save(accione);
        rspta.put("idCreado", accione.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(rspta);
    }
    //Exceptionhandlerpost
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST")){
            responseMap.put("Result", "Error");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
