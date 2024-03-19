package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for user information retrieved through OpenID Connect (OIDC) authentication.
 * This class encapsulates the attributes of a user authenticated via OIDC.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OIDCUserInformationDTO {

    /**
     * A map of attributes returned from the OIDC provider. Includes user profile information such as name and email.
     */
    private Map<String, Object> attributes;

    /**
     * Retrieves the unique identifier ('sub') of the user as defined in OIDC.
     * @return The user's unique identifier.
     */
    public String getId() {
        return (String) attributes.get("sub");
    }

    /**
     * Retrieves the user's first name ('given_name') from OIDC attributes.
     * @return The user's first name.
     */
    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    /**
     * Retrieves the user's last name ('family_name') from OIDC attributes.
     * @return The user's last name.
     */
    public String getLastName() {
        return (String) attributes.get("family_name");
    }

    /**
     * Retrieves the user's email address from OIDC attributes.
     * @return The user's email address.
     */
    public String getEmail() {
        return (String) attributes.get("email");
    }

    /**
     * Retrieves the user's profile picture URL ('picture') from OIDC attributes.
     * @return The URL of the user's profile picture.
     */
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    /**
     * Retrieves the claims associated with the user from OIDC attributes.
     * @return The claims of the user.
     */
    public String getClaims() {
        return (String) attributes.get("claims");
    }

}
