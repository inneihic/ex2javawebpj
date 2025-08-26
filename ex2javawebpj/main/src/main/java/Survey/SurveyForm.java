package Survey;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

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

    private String heardFrom; // Search engine, Word of mouth, Social Media, Other
    private boolean wantsAnnouncements;
    private boolean wantsEmailAnnouncements;
    private String contactPreference; // Email or postal mail, Email only, Postal mail only

    // getters and setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDayOfBirth() { return dayOfBirth; }
    public void setDayOfBirth(LocalDate dayOfBirth) { this.dayOfBirth = dayOfBirth; }
    public String getHeardFrom() { return heardFrom; }
    public void setHeardFrom(String heardFrom) { this.heardFrom = heardFrom; }
    public boolean isWantsAnnouncements() { return wantsAnnouncements; }
    public void setWantsAnnouncements(boolean wantsAnnouncements) { this.wantsAnnouncements = wantsAnnouncements; }
    public boolean isWantsEmailAnnouncements() { return wantsEmailAnnouncements; }
    public void setWantsEmailAnnouncements(boolean wantsEmailAnnouncements) { this.wantsEmailAnnouncements = wantsEmailAnnouncements; }
    public String getContactPreference() { return contactPreference; }
    public void setContactPreference(String contactPreference) { this.contactPreference = contactPreference; }
}
