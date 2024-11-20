package cz.oksystem.aihackathon.writerassistant.dto;

import java.util.List;

public class LoadedModelsResponseDto {

    private List<String> models;

    public LoadedModelsResponseDto(List<String> models) {
        this.models = models;
    }

    public LoadedModelsResponseDto() {
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }
}
