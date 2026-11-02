package com.resumeai.controller;
import com.fasterxml.jackson.databind.ObjectMapper; import com.resumeai.model.ResumeRecord; import com.resumeai.repository.ResumeRepository; import org.springframework.http.*; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestController @RequestMapping("/api/resumes") public class ResumeController {
 private final ResumeRepository repo; private final ObjectMapper mapper; public ResumeController(ResumeRepository r,ObjectMapper m){repo=r;mapper=m;}
 private Long uid(){return ((Number)SecurityContextHolder.getContext().getAuthentication().getDetails()).longValue();}
 @GetMapping public List<Map> all()throws Exception{List<Map> out=new ArrayList<>();for(var r:repo.findAllByUserId(uid()))out.add(mapper.readValue(r.getData(),Map.class));return out;}
 @PostMapping public ResponseEntity<?> save(@RequestBody Map<String,Object> data)throws Exception{String id=String.valueOf(data.getOrDefault("id",UUID.randomUUID().toString()));ResumeRecord r=repo.findByIdAndUserId(id,uid()).orElseGet(ResumeRecord::new);r.setId(id);r.setUserId(uid());r.setData(mapper.writeValueAsString(data));r.setUpdatedAt(Instant.now());repo.save(r);return ResponseEntity.ok(data);}
 @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable String id){return repo.findByIdAndUserId(id,uid()).map(r->{repo.delete(r);return ResponseEntity.noContent().build();}).orElse(ResponseEntity.notFound().build());}
}
