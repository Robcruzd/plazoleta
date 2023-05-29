package com.pragma.plazoleta.domain.model;

import com.pragma.plazoleta.domain.exception.DomainException;

public class RestaurantModel {

    private Long id;
    private String name;
    private Long nit;
    private String address;
    private String phone;
    private String urlLogo;
    private Long ownerId;

    public RestaurantModel() {
    }

    public RestaurantModel(Long id, String name, Long nit, String address, String phone, String urlLogo, Long ownerId) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.address = address;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.ownerId = ownerId;
    }

    public void validate() {
        validateRequiredFields();
        validateNit();
        validatePhoneNumber();
        validateName();
    }

    private void validateRequiredFields() {
        if (name == null || nit == null || address == null || phone == null || urlLogo == null || ownerId == null) {
            throw new DomainException("Todos los campos son obligatorios");
        }
    }

    private void validateNit() {
        if (!isValidNit(nit)) {
            throw new DomainException("El formato del NIT es invalido");
        }
    }

    private void validatePhoneNumber() {
        if (phone.length() > 13 || !isValidPhoneNumber(phone)) {
            throw new DomainException("El formato del celular es invalido");
        }
    }

    private void validateName() {
        if(!isValidName(name)) {
            throw new DomainException("El formato del nombre es invalido");
        }
    }

    private boolean isValidName(String email) {
        String emailRegex = "^(?=.*[a-zA-Z])[\\w\\d ]+$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?\\d{6,13}$";
        return phoneNumber.matches(phoneRegex);
    }

    private boolean isValidNit(Long nit) {
        String documentRegex = "^[0-9]+$";
        return nit.toString().matches(documentRegex);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
