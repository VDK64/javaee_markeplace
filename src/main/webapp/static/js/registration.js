"use strict";

let firstNameError = false;
let lastNameError = false;
let emailError = false;
let passwordError = false;
let confirmPasswordError = false;
let cyrillicRegex = new RegExp("[А-Яа-я]");
let emailRegex = new RegExp("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
let passwordRegex = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_!@#$%^&*+=])(?=\\S+$).{4,15}$");

let checkFristName = () => {
    let input = $("input[name=firstname]");
    let haveCyrillic = input.val().match(cyrillicRegex) !== null;
    if (!firstNameError && haveCyrillic) {
        firstNameError = haveCyrillic;
        input.removeClass("input");
        input.addClass("error-password");
        input.after("<p class='error error-firstname'>Latin letters ony!</p>");
    }
    if (firstNameError && !haveCyrillic) {
        firstNameError = haveCyrillic;
        $(".error-firstname").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    checkFieldsOnEmpty();
}

let checkLastName = () => {
    let input = $("input[name=lastname]");
    let haveCyrillic = input.val().match(cyrillicRegex) !== null;
    if (!lastNameError && haveCyrillic) {
        lastNameError = haveCyrillic;
        input.removeClass("input");
        input.addClass("error-password");
        input.after("<p class='error error-lastname'>Latin letters ony!</p>");
    }
    if (lastNameError && !haveCyrillic) {
        lastNameError = haveCyrillic;
        input.removeClass("input");
        input.addClass("error-password");
        $(".error-lastname").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    checkFieldsOnEmpty();
}

let checkEmail = () => {
    let input = $("input[name=email]");
    let invalidEmail = input.val().match(emailRegex) == null;
    if (!emailError && invalidEmail) {
        emailError = invalidEmail;
        input.removeClass("input");
        input.addClass("error-password");
        input.after("<p class='error error-email'>Invalid email!</p>");
    }
    if (emailError && !invalidEmail) {
        emailError = invalidEmail;
        $(".error-email").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    if (input.val() == "" || input.val() == null) {
        emailError = false;
        $(".error-email").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    checkFieldsOnEmpty();
}

let checkPasswordOnValid = () => {
    let input = $("input[name=credentials]");
    comparePasswordAndConfirmation();
    let invalidPassword = input.val().match(passwordRegex) == null;
    if (!passwordError && invalidPassword) {
        passwordError = invalidPassword;
        input.after("<p class='error-password-notice error'>Wrong password. Read info above.</p>");
        input.removeClass("input");
        input.addClass("error-password");
    }
    if (passwordError && !invalidPassword) {
        passwordError = invalidPassword;
        $(".error-password-notice").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    if (input.val() == "" || input.val() == null) {
        passwordError = false;
        $(".error-password-notice").remove();
        input.removeClass("error-password");
        input.addClass("input");
    }
    checkFieldsOnEmpty();
}

let comparePasswordAndConfirmation = () => {
    let password = $("input[name=credentials]");
    let confirm = $("input[name=confirmCredentials]");
    if (confirm.val() !== password.val() && !confirmPasswordError) {
        confirmPasswordError = true;
        password.after("<p class='error-password-confirmation-error error'>Passwords are not identical.</p>");
    }
    if (confirm.val() === password.val() && confirmPasswordError) {
        confirmPasswordError = false;
        $(".error-password-confirmation-error").remove();
    }
    checkFieldsOnEmpty();
}

let checkFieldsOnEmpty = () => {
    let firstName = $("input[name=firstname]").val();
    let lastName = $("input[name=lastname]").val();
    let email = $("input[name=email]").val();
    let password = $("input[name=credentials]").val();
    let confirmPassword = $("input[name=confirmCredentials]").val();
    if (firstName && lastName && email && password && confirmPassword
        && !firstNameError && !lastNameError && !emailError &&
         !passwordError && !confirmPasswordError) {
        $(".green-button").prop("disabled", false);
    } else {
        $(".green-button").prop("disabled", true);
    }
}