package config.ribbon;

import com.goblin.contentcenter.config.NacosSameClusterWeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    @Bean
    public IRule ribbonRule(){
        return new NacosSameClusterWeightedRule();
    }
}
