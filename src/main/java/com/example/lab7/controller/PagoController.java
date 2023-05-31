package com.example.lab7.controller;

import com.example.lab7.entities.Pago;
import com.example.lab7.repository.PagoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/pagos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class PagoController {
    final PagoRepository pagoRepository;

    public PagoController(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @GetMapping(value = "/listarPagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    @PostMapping("/registrarPago")
    public ResponseEntity<HashMap<String, Object>> registrarPago(@RequestBody Pago pago){
        HashMap<String, Object> rspta = new HashMap<>();
        pagoRepository.save(pago);
        rspta.put("id", pago.getId());
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
