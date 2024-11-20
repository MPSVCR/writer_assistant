package cz.oksystem.aihackathon.writerassistant.service.finetuning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReportLineTranscriber {

    private static final int limitOfLines = 200;

    private final ObjectMapper mapper = new ObjectMapper();

    private final Path sourcePath;
    private final Path sinkPath;

    public ReportLineTranscriber(Path sourcePath, Path sinkPath) {
        Objects.requireNonNull(sourcePath);
        Objects.requireNonNull(sinkPath);

        this.sourcePath = sourcePath;
        this.sinkPath = sinkPath;
    }

    public void apply(Function<String, ?> transformation) throws IOException {
        File sinkFile = new File(sinkPath.toUri());

        sinkFile.createNewFile();
        sinkFile.setWritable(true);

        try (Stream<String> lines = Files.lines(sourcePath)) {
            Files.write(sinkFile.toPath(), (Iterable<String>) lines.limit(limitOfLines).map(l -> {
                try {
                    return mapper.readValue(l, ReportInputLine.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).map(ReportInputLine::value).map(transformation).map(e -> {
                try {
                    return mapper.writeValueAsString(e);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            })::iterator);
        }
    }

    private record ReportInputLine(String TEXT_SETRENI) {
        String value() {
            return TEXT_SETRENI;
        }
    }
}
