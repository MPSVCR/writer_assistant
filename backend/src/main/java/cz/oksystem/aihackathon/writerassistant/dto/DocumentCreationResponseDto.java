package cz.oksystem.aihackathon.writerassistant.dto;

import java.util.List;

public class DocumentCreationResponseDto {

    private List<String> choices;

    public DocumentCreationResponseDto() {
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
