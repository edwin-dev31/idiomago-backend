package com.linguanova.idiomago.util;

public class AppRoutes {
    public static final String BACKEND_BASE_URL = "https://idiomago.koyeb.app/idiomago";
    public static final String FRONTEND_BASE_URL = "https://delightful-field-0bf6cfa0f.2.azurestaticapps.net";

    public static final String VERIFY_EMAIL_ENDPOINT = BACKEND_BASE_URL + "/auth/verify-email?token=";
    public static final String FRONTEND_REDIRECTION_URL = FRONTEND_BASE_URL + "/oauth2-success?token=";

    public static final String IMAGES_BASE_URL = "https://api.unsplash.com/search/photos";
    public static final String LANGUAGE_BASE_URL = "https://libretranslate.com/languages";
    public static final String OPENAI_BASE_URL = "https://api.openai.com/v1/chat/completions";
    public static final String CLOUDYNARY_URL = "https://api.cloudinary.com/v1_1/";
}
