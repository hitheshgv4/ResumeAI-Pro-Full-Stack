package com.resumeai.controller;
import com.fasterxml.jackson.databind.ObjectMapper; import com.resumeai.model.JobApplication; import com.resumeai.repository.ApplicationRepository; import org.springframework.http.*; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/applications") public class ApplicationController {
 private final ApplicationRepository repo;private final ObjectMapper mapper;public ApplicationController(ApplicationRepository r,ObjectMapper m){repo=r;mapper=m;}
 private Long uid(){return ((Number)SecurityContextHolder.getContext().getAuthentication().getDetails()).longValue();}
 @GetMapping public List<Map> all()throws Exception{List<Map> out=new ArrayList<>();for(var a:repo.findAllByUserId(uid()))out.add(mapper.readValue(a.getData(),Map.class));return out;}
 @PostMapping public ResponseEntity<?> save(@RequestBody Map<String,Object> data)throws Exception{String id=String.valueOf(data.getOrDefault("id",UUID.randomUUID().toString()));JobApplication a=repo.findById(id).filter(x->x.getUserId().equals(uid())).orElseGet(JobApplication::new);a.setId(id);a.setUserId(uid());a.setData(mapper.writeValueAsString(data));repo.save(a);return ResponseEntity.ok(data);}
 @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable String id){return repo.findById(id).filter(a->a.getUserId().equals(uid())).map(a->{repo.delete(a);return ResponseEntity.noContent().build();}).orElse(ResponseEntity.notFound().build());}
}
