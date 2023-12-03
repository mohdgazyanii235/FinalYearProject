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

@Aspect
@Component
@AllArgsConstructor
public class AdminActionAuthorizeAspect {

    private final UserService userService;

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
