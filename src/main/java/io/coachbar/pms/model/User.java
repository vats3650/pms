package io.coachbar.pms.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import io.coachbar.pms.util.Role;
import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String username;
    private String password;
    @NonNull
    private List<Role> roles;
}
