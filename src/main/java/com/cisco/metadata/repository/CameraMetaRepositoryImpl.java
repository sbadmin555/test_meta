package com.cisco.metadata.repository;

import com.cisco.metadata.model.CameraMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class CameraMetaRepositoryImpl implements CameraMetaRepository {

    private static final String DEFAULT_KEY = "CameraMetadata";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public CameraMetaRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> findAllCameraMeta() {
        return findAllCameraMeta(null);
    }

    @Override
    public Map<Object, Object> findAllCameraMeta(String deviceId) {

//        RedisOperations redisOperations = hashOperations.getOperations();
//        redisOperations.
        return hashOperations.entries(deviceId !=null?deviceId:DEFAULT_KEY);
    }

    @Override
    public void add(String deviceId, CameraMetadata camMeta) {
        hashOperations.put(deviceId !=null?deviceId:DEFAULT_KEY, camMeta.getId(), camMeta);
    }

    @Override
    public void delete(String deviceId, String id) {
        hashOperations.delete(deviceId !=null?deviceId:DEFAULT_KEY, id);
    }

    @Override
    public CameraMetadata findCamMeta(String deviceId, String id) {
        return (CameraMetadata) hashOperations.get(deviceId !=null?deviceId:DEFAULT_KEY, id);
    }
}
