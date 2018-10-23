package com.json.ignore.request;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Session request class
 * <p>
 * This is request class used to help working with Http Session
 */
public class RequestSession {
    private HttpSession session;

    public RequestSession(ServerHttpRequest request) throws IllegalArgumentException {
        this.session = getSession(request);
    }

    public HttpSession getSession() {
        return session;
    }

    /**
     * Gets session from request
     * <p>
     * If request is instance of {@link ServletServerHttpRequest} returns session. Another way return null
     *
     * @param serverHttpRequest {@link ServerHttpRequest} http request
     * @return {@link HttpSession} session, else null
     * @throws IllegalArgumentException when request doesn't have HttpSession
     */
    private HttpSession getSession(ServerHttpRequest serverHttpRequest) throws IllegalArgumentException {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            return servletRequest.getServletRequest().getSession();
        } else
            throw new IllegalArgumentException();
    }

    /**
     * Get attribute value from session attributes
     *
     * @param attributeName {@link String} attribute name
     * @return {@link Object} attribute value if exists, else null
     */
    public Object getSessionProperty(String attributeName) {
        return session.getAttribute(attributeName);
    }

    /**
     * Check if session properties has property
     * <p>
     * Find property with name and value specified in method params
     *
     * @param attributeName  {@link String} attribute name
     * @param attributeValue {@link String} expected attribute value
     * @return {@link Boolean} true if property with name and value is exist, else false
     */
    public boolean isSessionPropertyExists(String attributeName, String attributeValue) {
        Object sessionObject = getSessionProperty(attributeName);
        return Objects.equals(sessionObject, attributeValue);
    }
}