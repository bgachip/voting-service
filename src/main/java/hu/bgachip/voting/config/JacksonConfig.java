package hu.bgachip.voting.config;

import tools.jackson.databind.cfg.CoercionAction;
import tools.jackson.databind.cfg.CoercionInputShape;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import tools.jackson.databind.module.SimpleModule;

import java.time.Instant;

@Configuration
public class JacksonConfig {

    @Bean
    public JacksonJsonHttpMessageConverter jacksonJsonHttpMessageConverter(
            JsonMapper.Builder builder
    ) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Instant.class, new StrictInstantDeserializer());

        JsonMapper mapper = builder
                .build()
                .rebuild()
                .withCoercionConfig(String.class, config -> {
                    config.setCoercion(CoercionInputShape.Integer, CoercionAction.Fail);
                    config.setCoercion(CoercionInputShape.Float, CoercionAction.Fail);
                    config.setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail);
                })
                .addModule(module)
                .build();

        return new JacksonJsonHttpMessageConverter(mapper);
    }
}