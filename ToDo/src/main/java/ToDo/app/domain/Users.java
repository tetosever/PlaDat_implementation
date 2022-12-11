package ToDo.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Setter(AccessLevel.NONE) private String email;

    public void setEmail(String email) {
        if (patternMatches(email)){
            this.email = email;
        }
        else {
            throw new PatternSyntaxException("Wrong email syntax", "^(.+)@(\\S+)$", -1);
        }

    }

    private static boolean patternMatches(String email) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(email)
                .matches();
    }
}
