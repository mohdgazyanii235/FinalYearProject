package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.OIDCUserInformationDTO;
import com.fyp.erpapi.erpapi.data.JoinCompanyDTO;
import com.fyp.erpapi.erpapi.data.OnBoardingCompleteDTO;
import com.fyp.erpapi.erpapi.data.UserRoleDTO;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.exception.NoSuchCompanyException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing user-related operations such as registration, role assignment, and onboarding.
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CompanyService companyService;


    /**
     * Loads a user by username (email in this context). This method is used by Spring Security
     * during the authentication process.
     *
     * @param username The email of the user to load.
     * @return UserDetails of the user found by email.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }


    /**
     * Registers a new user with the information obtained from an OIDC authentication flow.
     *
     * @param OIDCUserInformationDTO Data transfer object containing user information from the OIDC provider.
     * @param idToken The OIDC ID token.
     * @param userInfo The OIDC user info.
     * @param ssoIssuer The issuer of the SSO.
     * @return An {@link OidcUser} representing the newly registered user.
     * @throws NoSuchRoleException If a necessary role does not exist.
     * @throws AlreadyExistsException If the user already exists.
     */
    public OidcUser registerUser(OIDCUserInformationDTO OIDCUserInformationDTO, OidcIdToken idToken, OidcUserInfo userInfo, SSOIssuer ssoIssuer) throws NoSuchRoleException, AlreadyExistsException {

        if (this.existsByEmail(OIDCUserInformationDTO.getEmail())) {
            throw new AlreadyExistsException("A user with that email address already exists in the system");
        }

        System.out.println(OIDCUserInformationDTO.getClaims());
        User user = new User();
        user.setEmail(OIDCUserInformationDTO.getEmail());
        user.setFirstName(OIDCUserInformationDTO.getFirstName());
        user.setLastName(OIDCUserInformationDTO.getLastName());
        user.setPassword(UUID.randomUUID().toString());
        user.setImageUrl(OIDCUserInformationDTO.getImageUrl());
        user.addRole(this.roleService.getRoleByName("NON_ONBOARDED_USER_A"));
        user.setSsoIssuer(ssoIssuer);
        this.userRepository.save(user);
        return new DefaultOidcUser(user.getAuthorities(), idToken, userInfo);
    }

    /**
     * Assigns roles to a user identified by email.
     *
     * @param userRoleDTO Data transfer object containing the email of the user and the roles to assign.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public void assignRolesToUser(UserRoleDTO userRoleDTO) {
        Optional<User> userOptional = this.userRepository.getUserByEmail(userRoleDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRoleDTO.getRoleNames().forEach(roleName -> {
                try {
                    user.addRole(this.roleService.getRoleByName(roleName));
                } catch (NoSuchRoleException e) {
                    throw new RuntimeException(e);
                }
            });
            this.userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(userRoleDTO.getEmail());
        }
    }

    /**
     * Retrieves the first name of a user by their email.
     *
     * @param email The email of the user.
     * @return The first name of the user, or null if not found.
     */
    public String getFirstName(String email) {
        Optional<String> firstNameOptional = this.userRepository.getFirstNameByEmail(email);
        return firstNameOptional.orElse(null);
    }

    /**
     * Retrieves the last name of a user by their email.
     *
     * @param email The email of the user.
     * @return The last name of the user, or null if not found.
     */
    public String getLastName(String email) {
        Optional<String> lastNameOptional = this.userRepository.getLastNameByEmail(email);
        return lastNameOptional.orElse(null);
    }

    /**
     * Updates the first name of a user by their email.
     *
     * @param email The email of the user.
     * @param firstName The new first name to be updated to.
     */
    public void updateFirstName(String email, String firstName) {
        this.userRepository.updateFirstName(email, firstName);
    }

    /**
     * Updates the last name of a user by their email.
     *
     * @param email The email of the user.
     * @param lastName The new last name to be updated to.
     */
    public void updateLastName(String email, String lastName) {
        this.userRepository.updateLastName(email, lastName);
    }

    /**
     * Updates the role of a user during the onboarding process.
     *
     * @param email The email of the user.
     * @param roleName The new role to be assigned.
     */
    public void updateRole(String email, String roleName) {
        User user = (User) this.loadUserByUsername(email);
        try {
            Role onBoardedUserA = this.roleService.getRoleByName("NON_ONBOARDED_USER_A");
            Role onBoardedUserB = this.roleService.getRoleByName("NON_ONBOARDED_USER_B");
            Role onBoardedUserC = this.roleService.getRoleByName("NON_ONBOARDED_USER_C");
            if (roleName.equals("NON_ONBOARDED_USER_B") && user.hasRole("NON_ONBOARDED_USER_A")) {
                user.getRoles().remove(onBoardedUserA);
                user.addRole(onBoardedUserB);
            } else if (roleName.equals("NON_ONBOARDED_USER_C") && user.hasRole("NON_ONBOARDED_USER_B")) {
                user.getRoles().remove(onBoardedUserB);
                user.addRole(onBoardedUserC);
            } else if (roleName.equals("USER") && user.hasRole("NON_ONBOARDED_USER_C")) {
                Role userRole = this.roleService.getRoleByName("USER");
                user.getRoles().remove(onBoardedUserC);
                user.addRole(userRole);
            }
            OAuth2AuthenticationToken currentAuthenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationToken newAuthenticationToken = new OAuth2AuthenticationToken(currentAuthenticationToken.getPrincipal(), user.getAuthorities(), currentAuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(newAuthenticationToken);
            this.userRepository.save(user);
        } catch (NoSuchRoleException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Associates a user with a company during the onboarding process.
     *
     * @param joinCompanyDTO Data transfer object containing the email of the user and the name of the company to join.
     * @throws NoSuchCompanyException if the specified company does not exist.
     */
    public void joinCompany(JoinCompanyDTO joinCompanyDTO) throws NoSuchRoleException, NoSuchCompanyException {
        User user = (User) this.loadUserByUsername(joinCompanyDTO.getEmail());
        if (this.companyService.getCompanyByName(joinCompanyDTO.getCompanyName()) == null) {
            throw new NoSuchCompanyException("Company does not exist");
        }
        user.setCompany(this.companyService.getCompanyByName(joinCompanyDTO.getCompanyName()));
        this.userRepository.save(user);
    }

    /**
     * Validates and finalizes the onboarding process for a user.
     *
     * @param onBoardingCompleteDTO Data transfer object containing the email of the user completing the onboarding.
     * @throws NoSuchRoleException if the specified role does not exist.
     */
    public void validateOnboardingComplete(OnBoardingCompleteDTO onBoardingCompleteDTO) throws NoSuchRoleException {
        User user = (User) this.loadUserByUsername(onBoardingCompleteDTO.getEmail());
        if (user.getCompany()!=null && user.getFirstName()!=null && user.getLastName()!=null) {
            user.getRoles().remove(this.roleService.getRoleByName("NON_ONBOARDED_USER_C"));
            if (!user.isAdmin()) {
                user.addRole(this.roleService.getRoleByName("USER"));
            }
            user.setIsOnboardingComplete(true);
            this.userRepository.save(user);
        }
    }

    /**
     * 
     * @param email
     * @return
     */
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
