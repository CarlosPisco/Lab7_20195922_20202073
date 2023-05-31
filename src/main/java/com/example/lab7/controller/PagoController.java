package com.example.lab7.controller;

import com.example.lab7.entities.Pago;
import com.example.lab7.entities.Usuario;
import com.example.lab7.repository.PagoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        HashMap<String, Object> responseJson = new HashMap<>();
        if(pago.getId()!=null && pago.getId()>0){
            boolean flag = false;
            for (Pago pago1 : pagoRepository.findAll()){
                if(Objects.equals(pago1.getId(), pago.getId())){
                    flag = true;
                    break;
                }

            }

            if(!flag){
                pagoRepository.save(pago);
                responseJson.put("id creado",pago.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
            }else{
                responseJson.put("error","El id del pago a crear ya existe");
                return ResponseEntity.badRequest().body(responseJson);
            }

        }else{
            responseJson.put("error","El id brindado debe ser mayor igual a 0 y diferente de null");
            return ResponseEntity.badRequest().body(responseJson);
        }
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
