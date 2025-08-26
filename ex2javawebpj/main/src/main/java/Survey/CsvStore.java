package Survey;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.time.OffsetDateTime;

@Component
public class CsvStore {
    private final Path file = Paths.get("submissions.csv");

    public synchronized void save(SurveyForm f) {
        try {
            boolean exists = Files.exists(file);
            try (var w = Files.newBufferedWriter(file, exists ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
                if (!exists) {
                    w.write("timestamp,firstName,lastName,email,dayOfBirth,heardFrom,wantsAnnouncements,wantsEmailAnnouncements,contactPreference\n");
                }
                w.write(String.join(",",
                    OffsetDateTime.now().toString(),
                    esc(f.getFirstName()),
                    esc(f.getLastName()),
                    esc(f.getEmail()),
                    f.getDayOfBirth() == null ? "" : f.getDayOfBirth().toString(),
                    esc(f.getHeardFrom()),
                    String.valueOf(f.isWantsAnnouncements()),
                    String.valueOf(f.isWantsEmailAnnouncements()),
                    esc(f.getContactPreference())
                ));
                w.write("\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String esc(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\")) {
            return "\""" + s.replace("\\", "\\\\").replace("\"", "\\\"\"") + "\""";
        }
        return s;
    }
}
