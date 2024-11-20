package cz.oksystem.aihackathon.writerassistant.dto;

import java.util.List;
import java.util.Map;

public class ModelsConfiguration {

    private List<Map<String, Model>> models;

    public ModelsConfiguration() {
    }

    public List<Map<String, Model>> getModels() {
        return models;
    }

    public void setModels(List<Map<String, Model>> models) {
        this.models = models;
    }
}
