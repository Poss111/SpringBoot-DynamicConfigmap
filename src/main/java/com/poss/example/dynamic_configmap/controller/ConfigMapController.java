package com.poss.example.dynamic_configmap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@Slf4j
public class ConfigMapController {

    @Autowired
    private KubernetesClient kubernetesClient;

    @GetMapping("/configmap")
    public Map<String, String> getConfigMapDetails() {
        Map<String, String> configMapDetails = new HashMap<>();
        try {
            ConfigMap configMap = kubernetesClient.configMaps()
                    .inNamespace("default")
                    .withName("my-configmap")
                    .get();
            log.info(null != configMap ? configMap.getData().toString() : "ConfigMap not found");
            if (configMap != null && configMap.getData() != null) {
                configMapDetails.putAll(configMap.getData());
            }
        } catch (KubernetesClientException e) {
            e.printStackTrace();
        }
        return configMapDetails;
    }

}