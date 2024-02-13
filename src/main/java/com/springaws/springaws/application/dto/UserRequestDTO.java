package com.springaws.springaws.application.dto;

import org.hibernate.validator.constraints.br.CPF;

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    @CPF(message = "CPF inválido")
    private String cpf;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String firstName,
                          String lastName,
                          String email,
                          String cpf) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
