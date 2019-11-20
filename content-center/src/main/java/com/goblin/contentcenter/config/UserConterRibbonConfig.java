package com.goblin.contentcenter.config;

import config.ribbon.RibbonConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClients(defaultConfiguration = RibbonConfig.class)
public class UserConterRibbonConfig {
}
