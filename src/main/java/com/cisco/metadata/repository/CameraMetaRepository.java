package com.cisco.metadata.repository;

import com.cisco.metadata.model.CameraMetadata;

import java.util.Map;

public interface CameraMetaRepository {

    /**
     * Return all metadata
     */

    Map<Object, Object> findAllCameraMeta();

    /**
     * Return all metadata for a device
     */
    Map<Object, Object> findAllCameraMeta(String deviceId);

    /**
     * Add key-value pair to Redis.
     */
    void add(String deviceId, CameraMetadata camMeta);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String deviceId, String id);

    /**
     * find a movie
     */
    CameraMetadata findCamMeta(String deviceId, String id);
}
