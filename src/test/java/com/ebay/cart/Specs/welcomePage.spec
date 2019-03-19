@objects
    welcome-block       css     .jumbotron
    greeting            css     #welcome-page h1
    text-block-*        css     #welcome-page p
    login-button        css     #welcome-page .button-login


= DoNotAttend =
    @on *
        text-block-1, login-button, text-block-3:
            inside welcome-block ~30px left

        greeting:
            above text-block-1 10 to 50 px
            inside welcome-block ~ 30px left

        text-block-1:
            height > 20px
            above login-button 10 to 50 px

        login-button:
            height ~ 45px
            text is "Login"
            above text-block-3 10 to 50px
