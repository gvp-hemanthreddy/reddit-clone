package com.hemanth.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is empty")
    @Column(unique = true)
    private String email;

    @Length(min = 3, max = 15, message = "Must be at least 3 characters long")
    @Column(unique = true)
    private String username;

    @Length(min = 6, max = 100, message = "Must be at least 6 characters long")
    private String password;

    private boolean enabled;

    private Instant createdAt;

    private Instant updatedAt;
}
