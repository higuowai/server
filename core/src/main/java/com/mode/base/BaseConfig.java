package com.mode.base;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * System configurations for all the Java backend systems.
 *
 * @author chao
 *
 */
public class BaseConfig {

    /**
     * The return codes of server API calls.
     */
    public static final Integer OPERATION_SUCCEEDED = 0;
    public static final Integer OPERATION_FAILED = 1;

    /**
     * The success message of server API calls.
     */
    public static final String SUCCESSFUL_MESSAGE = "Operation Successful.";

    /**
     * The pre-defined roles of system users.
     */
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * The folder names of images that are stored on Aliyun OSS storage.
     */
    public static final String FOLDER_FEEDS = "feeds";
    public static final String FOLDER_POSTS = "posts";
    public static final String FOLDER_AVATARS = "avatars";

    /**
     * The default expire window for an access token. User will need to input the login username
     * and credentials again to refresh the token when it expires.
     */
    public static final Long TOKEN_EXPIRE_WINDOW = 1000l * 60 * 60 * 24 * 100;
}