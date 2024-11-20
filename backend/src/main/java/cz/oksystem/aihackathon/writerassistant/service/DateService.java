package cz.oksystem.aihackathon.writerassistant.service;

import com.azure.ai.openai.models.ChatCompletionsFunctionToolDefinition;
import com.azure.ai.openai.models.FunctionDefinition;
import com.azure.core.util.BinaryData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class DateService {
    public ChatCompletionsFunctionToolDefinition getToolDefinition() {
        return new ChatCompletionsFunctionToolDefinition(
                new FunctionDefinition("get_date")
                        .setDescription("Vrati spravne datum od dnesniho dne, dnes je nula, vcera -1, zitra 1")
                        .setParameters(BinaryData.fromObject(Map.of(
                                "type", "object",
                                "properties", Map.of(
                                        "when", Map.of(
                                                "type", "integer",
                                                "description", "cislo ktere znamena pocet dni od dnesniho dne. dnes je nula, vcera -1, zitra 1"
                                        )
                                ),
                                "required", List.of("when")
                        ))));
    };

    public String getResult(String functionArguments) {
        // Zde byste zpracovali argumenty a zavolali skutečnou funkci pro získání počasí
        return  getDateFromToday(getArgument(functionArguments, "when"));
    }

    public static String getDateFromToday(int days) {
        LocalDate date = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public static int getArgument(String arguments, String name) {
        ObjectMapper objectMapper = new ObjectMapper();
        int when = 0;

        try {
            JsonNode jsonNode = objectMapper.readTree(arguments);

            if (jsonNode.has(name)) {
                when = jsonNode.get(name).asInt();
            } else {
                System.out.println("Argument '" + name + "' nebyl nalezen.");
            }
        } catch (Exception e) {
            System.err.println("Chyba při parsování argumentů: " + e.getMessage());
        }

        return when;
    }
}
