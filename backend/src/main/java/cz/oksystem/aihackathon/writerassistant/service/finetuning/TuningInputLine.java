package cz.oksystem.aihackathon.writerassistant.service.finetuning;

import jakarta.annotation.Nonnull;

import java.util.List;

public record TuningInputLine(@Nonnull List<Message> messages) {

    public static TuningInputLine of(@Nonnull String systemPrompt, @Nonnull String tuningInput, @Nonnull String tuningOutput) {
        return new TuningInputLine(List.of(new Message(Role.system, systemPrompt), new Message(Role.user, tuningInput), new Message(Role.assistant, tuningOutput)));
    }

    public record Message(Role role, String content) {

    }

    public enum Role {
        assistant,
        system,
        user
    }
}
