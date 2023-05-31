package com.example.lab7.controllers;


import com.example.lab7.entities.Solicitude;
import com.example.lab7.repository.SolicitudRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    final SolicitudRepository solicitudRepository;

    public SolicitudController(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }


    @GetMapping("/lista")
    public List<Solicitude> listarSolicitudes(){
        return solicitudRepository.findAll();
    }

    @PostMapping("/registro")
    public ResponseEntity<HashMap<String,Object>> guardarSolicitud(@RequestBody Solicitude solicitude){

        HashMap<String,Object> responseJson = new HashMap<>();

        if(solicitude.getId()==null || solicitude.getId()<0){
            responseJson.put("error","Tiene que enviar un id, y este tiene que ser mayor de 0");
            return ResponseEntity.badRequest().body(responseJson);
        }else{

            boolean flag = false;
            for(Solicitude solicitude1 : solicitudRepository.findAll()){
                if(Objects.equals(solicitude1.getId(), solicitude.getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){

                if(solicitude.getSolicitudEstado().equals("") && solicitude.getSolicitudMonto()!=null){
                    solicitude.setSolicitudEstado("pendiente");
                    solicitudRepository.save(solicitude);
                    responseJson.put("Monto solicitado",solicitude.getSolicitudMonto());
                    responseJson.put("id",solicitude.getId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);

                }else{

                    if(!solicitude.getSolicitudEstado().equals("")){
                        responseJson.put("error estado","el campo solicitud estado debe estar vacio");
                    }


                    if(solicitude.getSolicitudMonto()==null){
                        responseJson.put("error monto","el campo solicitud monto no debe estar vacio");
                    }

                    return ResponseEntity.badRequest().body(responseJson);
                }

            }else{
                responseJson.put("error","El id ya existe");
                return ResponseEntity.badRequest().body(responseJson);
            }

        }


    }

    @PutMapping(value="/aprobarSolicitud")
    public ResponseEntity<HashMap<String,Object>> aprobarSolicitud (@RequestParam("idSolicitud") String id){

        HashMap<String ,Object> responseJson = new HashMap<>();

        try{
            Integer idInteger = Integer.valueOf(id);
            if(idInteger<=0){
                responseJson.put("error","el id debe ser mayor que 0");
                return ResponseEntity.badRequest().body(responseJson);
            }else{

                Optional<Solicitude> solicitudeOptional = solicitudRepository.findById(idInteger);

                if(solicitudeOptional.isPresent()){

                    Solicitude solicitude = solicitudeOptional.get();
                    if(solicitude.getSolicitudEstado().equalsIgnoreCase("pendiente")){
                        solicitude.setSolicitudEstado("aprobado");
                        solicitudRepository.save(solicitude);
                        responseJson.put("id solicitud",solicitude.getId());
                        return ResponseEntity.ok(responseJson);
                    }else{
                        responseJson.put("solicitud ya atendida",solicitude.getId());
                         return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseJson);
                    }

                }else{
                    responseJson.put("error","el id brindado no corresponde a ninguna solicitud");
                     return ResponseEntity.badRequest().body(responseJson);
                }



            }




        }catch (Exception e){
            responseJson.put("error","el id no puede ser nulo y debe ser un numero");
             return ResponseEntity.badRequest().body(responseJson);

        }


    }

    @PutMapping(value="/denegarSolicitud")
    public ResponseEntity<HashMap<String,Object>> denegarSolicitud (@RequestParam("idSolicitud") String id){

        HashMap<String ,Object> responseJson = new HashMap<>();

        try{
            Integer idInteger = Integer.valueOf(id);
            if(idInteger<=0){
                responseJson.put("error","el id debe ser mayor que 0");
                return ResponseEntity.badRequest().body(responseJson);
            }else{

                Optional<Solicitude> solicitudeOptional = solicitudRepository.findById(idInteger);

                if(solicitudeOptional.isPresent()){

                    Solicitude solicitude = solicitudeOptional.get();
                    if(solicitude.getSolicitudEstado().equalsIgnoreCase("pendiente")){
                        solicitude.setSolicitudEstado("denegado");
                        solicitudRepository.save(solicitude);
                        responseJson.put("id solicitud",solicitude.getId());
                        return ResponseEntity.ok(responseJson);
                    }else{
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseJson);
                    }

                }else{
                    responseJson.put("error","el id brindado no corresponde a ninguna solicitud");
                    return ResponseEntity.badRequest().body(responseJson);
                }



            }




        }catch (Exception e){
            responseJson.put("error","el id no puede ser nulo y debe ser un numero");
            return ResponseEntity.badRequest().body(responseJson);

        }


    }

    @DeleteMapping("/borrarSolicitud")
    public ResponseEntity<HashMap<String,Object>> borrarSolicitud (@RequestParam ("idSolicitud") String idStr){

        HashMap<String,Object> responseJson = new HashMap<>();

        try{

            int id = Integer.parseInt(idStr);

            Optional<Solicitude> solicitudeOptional = solicitudRepository.findById(id);

            if(id<=0){
                responseJson.put("error","El id no puede ser menor o igual que cero");
                return ResponseEntity.badRequest().body(responseJson);
            }else{
                if(solicitudeOptional.isPresent()){

                    Solicitude solicitude = solicitudeOptional.get();
                    if(solicitude.getSolicitudEstado().equalsIgnoreCase("denegado")){
                        responseJson.put("id solicitud borrada",solicitude.getId());
                        return ResponseEntity.ok(responseJson);
                    }else{
                        responseJson.put("error","la solicitud no presenta estado denegada");
                        return ResponseEntity.badRequest().body(responseJson);
                    }

                }else{
                    responseJson.put("error","EL id brindado no corresponde a ninguna solicitud");
                    return ResponseEntity.badRequest().body(responseJson);
                }
            }





        }catch (Exception e){

            responseJson.put("error","el id debe ser un numero y no dede ser nulo");
            return ResponseEntity.badRequest().body(responseJson);

        }



    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("Result", "Error");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
