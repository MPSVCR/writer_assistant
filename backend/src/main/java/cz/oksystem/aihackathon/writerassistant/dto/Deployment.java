package cz.oksystem.aihackathon.writerassistant.dto;

public enum Deployment {

    GPT4o("gpt-4o"),
    GPT4o_mini("gpt-4o-mini"),
    FTM_SMALL("gpt-4o-oksystem-2024-08-06-v1-100"),
    FTM_MEDIUM("gpt-4o-oksystem-2024-08-06-v1-200");

    Deployment(String val) {
        this.val = val;
    }

    private String val;

    public String getFullName() {
        return this.val;
    }

}
