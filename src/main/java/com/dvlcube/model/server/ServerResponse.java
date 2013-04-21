package com.dvlcube.model.server;

/**
 *
 * @author Wonka
 */
public enum ServerResponse {

    /**
     * Default string code for a successful ajax response.
     */
    SUCCESS("success;"),
    /**
     * Default string code for an unsuccessful ajax response.
     */
    FAIL("fail;"),
    /**
     * Default string code for an ajax warning response.
     */
    WARNING("warning;"),
    /**
     * Default string code for an "illegal operation" ajax response
     */
    ILLEGAL("illegal;");
    private String responseCode;

    ServerResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
