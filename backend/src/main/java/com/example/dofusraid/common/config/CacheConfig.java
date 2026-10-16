package com.example.dofusraid.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Active le cache Spring. Le fournisseur (Caffeine) et les caches sont declares
 * dans application.yml (spring.cache.*).
 */
@Configuration
@EnableCaching
public class CacheConfig {
}
