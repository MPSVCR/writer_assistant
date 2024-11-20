package cz.oksystem.aihackathon.writerassistant;

import cz.oksystem.aihackathon.writerassistant.dto.ModelsConfiguration;
import cz.oksystem.aihackathon.writerassistant.service.ConfigurationLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigurationLoaderTest {

    @Autowired
    private ConfigurationLoaderService configurationLoaderService;

    @Test
    public void loadConfiguration() {
        ModelsConfiguration configuration = configurationLoaderService.loadConfiguration();
        assert !configuration.getModels().isEmpty();
    }



}
