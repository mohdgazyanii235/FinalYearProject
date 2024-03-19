package com.fyp.erpapi.erpapi.annotation;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * Aspect for enforcing admin action authorization.
 * This aspect intercepts calls to methods annotated with {@link AdminActionAuthorize} and performs
 * security checks to ensure the currently authenticated user (admin) has the necessary authority
 * to perform the specified action on the target user.
 *
 * It leverages Spring Security's context to fetch the current user's authentication details and
 * compare them against the required authority specified in the method's {@link AdminActionAuthorize} annotation.
 */
@Aspect
@Component
@AllArgsConstructor
public class AdminActionAuthorizeAspect {

    private final UserService userService;

    /**
     * Intercepts method calls annotated with {@link AdminActionAuthorize} and authorizes the action
     * based on the admin's authorities and the action specified in the annotation.
     *
     * If the admin does not have the required authority to perform the action on the target user,
     * an {@link AccessDeniedException} is thrown, halting the method execution. Otherwise, the method
     * execution proceeds.
     *
     * @param joinPoint The join point representing the method call intercepted by this aspect.
     * @param adminActionAuthorize The annotation that triggered this aspect, containing details about
     *                             the action type and the subject.
     * @return The result of the method call if authorization succeeds.
     * @throws Throwable if there is an error during method execution or if access is denied.
     */
    @Around("@annotation(adminActionAuthorize)")
    public Object authorizeAdminActionOnSubject(ProceedingJoinPoint joinPoint, AdminActionAuthorize adminActionAuthorize) throws Throwable {

        String actionSubjectEmail = adminActionAuthorize.actionSubjectEmail();
        User actionSubject = (User) userService.loadUserByUsername(actionSubjectEmail);
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (adminActionAuthorize.actionType().equals(AdminActionType.WRITE_ROLES)) {
            if (admin.hasAuthority("WRITE_" + actionSubject.getCompany().getName().toUpperCase() + "_ROLES")){
                return joinPoint.proceed();
            } else {
                throw new AccessDeniedException("Admin doesn't have authority to do that");
            }
        } else if (adminActionAuthorize.actionType().equals(AdminActionType.WRITE_AUTHORITIES)) {
            if (admin.hasAuthority("WRITE_" + actionSubject.getCompany().getName().toUpperCase() + "_AUTHORITIES")){
                return joinPoint.proceed();
            } else {
                throw new AccessDeniedException("Admin doesn't have authority to do that");
            }
        }
        throw new AccessDeniedException("Admin doesn't have authority to do that");
    }
}
