package kr.co.promisemomo.module.promise.controller;

import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PromiseRestController {

    public final PromiseService promiseService;
    

    @PostMapping("/member/{id}/promise")
    public HttpEntity<Object> addPromise(@PathVariable("id") Long id, @RequestBody Promise paramPromise){
        Promise promise = promiseService.addPromise(id,paramPromise);
        return ResponseEntity.ok(paramPromise);
    }

    @GetMapping("/member/{id}/promises")
    public List<Promise> getPromises(@PathVariable("id") Long id){
        return promiseService.getPromises(id);
    }
    
    
    
    
}
