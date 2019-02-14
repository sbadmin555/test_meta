package com.cisco.metadata.controller;

import java.util.HashMap;
import java.util.Map;

import com.cisco.metadata.model.CameraMetadata;
import com.cisco.metadata.repository.CameraMetaRepository;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@SwaggerDefinition(
        info = @Info(
                description = "Operations pertaining to Camera Metadata Definitions",
                version = "V1",
                title = "The CameraMetadata Definition API")
)
public class WebController {
    
    @Autowired
    private CameraMetaRepository cameraMetaRepository;


    @RequestMapping(value = "/camMetadata", method = RequestMethod.POST)
    public ResponseEntity<String> add(
            @RequestParam String deviceId,
            @RequestBody CameraMetadata camMeta) {

        cameraMetaRepository.add(deviceId,camMeta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/camMetadata/{deviceId}", method = RequestMethod.GET)
    public @ResponseBody Map<String, CameraMetadata> findAllCamMeta(@PathVariable("deviceId") String deviceId) {
        Map<Object, Object> aa = cameraMetaRepository.findAllCameraMeta(deviceId);
        Map<String, CameraMetadata> map = new HashMap();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, (CameraMetadata)aa.get(key));
        }
        return map;
    }

    @RequestMapping(value = "/camMetadata/{deviceId}/{metadataId}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteMCamMeta(@PathVariable("deviceId") String deviceId,
                                                @PathVariable("metadataId") String metadataId) {
        cameraMetaRepository.delete(deviceId,metadataId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
