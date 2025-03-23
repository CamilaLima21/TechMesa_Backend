package com.fiap.techmesa.application.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    private Integer id;
    
    @Size(max = 100, message = "Name length must be less than 100 characters")
    @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
    private String name;
    
    @Size(max = 100, message = "Email length must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String email;
    
    private LocalDate registrationDate;

    private Integer addressId;
    
    private List<Reserve> reserves;
        
    public static Client createClient(
            final String name,
            final String email,
            final LocalDate registrationDate,
            final Integer addressId,
            final List<Reserve> reserves) {
                return new Client(null, name, email, registrationDate, addressId, reserves);
    }
}
