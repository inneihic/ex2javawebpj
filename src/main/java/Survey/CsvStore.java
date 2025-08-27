package Survey;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CsvStore {
    private static final Logger logger = LoggerFactory.getLogger(CsvStore.class);
    private final Path file;

    public CsvStore() {
        // Use environment variable for file path, default to submissions.csv
        String filePath = System.getenv("CSV_STORAGE_PATH") != null ?
                System.getenv("CSV_STORAGE_PATH") : "submissions.csv";
        this.file = Paths.get(filePath);
    }

    public synchronized void save(SurveyForm f) {
        try {
            boolean exists = Files.exists(file);
            Files.createDirectories(file.getParent()); // Ensure parent directory exists
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
                logger.info("Successfully saved survey form for email: {}", f.getEmail());
            }
        } catch (IOException e) {
            logger.error("Failed to save survey form", e);
            throw new UncheckedIOException(e);
        }
    }

    private String esc(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r") || s.contains("\t")) {
            return "\"" + s.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t") + "\"";
        }
        return s;
    }
}