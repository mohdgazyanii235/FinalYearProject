package com.fyp.erpapi.erpapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to authorize administrative actions within the application.
 * This annotation can be applied to methods to specify that they require
 * administrative authorization based on the action's type and the subject of the action,
 * identified by their email.
 *
 * Usage involves annotating methods that perform administrative tasks, specifying
 * the type of action being performed and the subject (user) the action is being
 * performed on. This allows for a flexible and declarative approach to enforcing
 * administrative controls within the application.
 *
 * Example:
 * <pre>
 * {@code
 * @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_AUTHORITIES)
 * @PreAuthorize("hasRole('ADMIN')")
 * @PostMapping("/createNewAuthority")
 * public ResponseEntity<?> createNewAuthority(@RequestBody AuthorityDTO authorityDTO) {
 *     // Do controller things...
 * }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdminActionAuthorize {


    /**
     * Specifies the email of the subject (user) upon whom the action is to be taken.
     * This identifies the target user of an admin action.
     *
     * @return The email of the action subject.
     */
    String actionSubjectEmail();

    /**
     * Specifies the type of administrative action being authorized.
     * This is used to determine the nature of the action for authorization purposes.
     *
     * @return The type of the admin action as defined in {@link AdminActionType}.
     */
    AdminActionType actionType();
}
