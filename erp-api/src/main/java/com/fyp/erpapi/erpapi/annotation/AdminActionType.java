package com.fyp.erpapi.erpapi.annotation;

/**
 * Enumerates types of administrative actions that require authorization.
 * This enum is used in conjunction with the {@link AdminActionAuthorize} annotation
 * to specify the kind of administrative action being taken on a user or resource within the application.
 *
 * The administrative actions currently supported are:
 * - WRITE_ROLES: Indicates an action that involves modifying roles of a user or resource.
 * - WRITE_AUTHORITIES: Indicates an action that involves modifying the authorities or permissions
 *   assigned to a user or resource.
 */
public enum AdminActionType {

    /**
     * Represents an administrative action to write or modify roles associated with a user or resource.
     */
    WRITE_ROLES,

    /**
     * Represents an administrative action to write or modify authorities associated with a user or resource.
     */
    WRITE_AUTHORITIES
}
