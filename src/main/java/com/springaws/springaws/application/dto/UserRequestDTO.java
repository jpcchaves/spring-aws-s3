package com.springaws.springaws.application.dto;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    @CPF(message = "CPF inválido")
    private String cpf;
    private MultipartFile profileImage;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String firstName,
                          String lastName,
                          String email,
                          String cpf,
                          MultipartFile profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.profileImage = profileImage;
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

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}
