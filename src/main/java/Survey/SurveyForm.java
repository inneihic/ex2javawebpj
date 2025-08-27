package Survey;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyForm {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Past(message = "Day of Birth must be in the past")
    private LocalDate dayOfBirth;

    @Pattern(regexp = "Search engine|Word of mouth|Social Media|Other", message = "Invalid source")
    private String heardFrom;

    private boolean wantsAnnouncements;
    private boolean wantsEmailAnnouncements;

    @Pattern(regexp = "Email or postal mail|Email only|Postal mail only", message = "Invalid contact preference")
    private String contactPreference;
}