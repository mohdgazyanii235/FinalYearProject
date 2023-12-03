//package com.fyp.erpapi.erpapi.database;
//
//import com.fyp.erpapi.erpapi.entity.Authority;
//import com.fyp.erpapi.erpapi.entity.Role;
//import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
//import com.fyp.erpapi.erpapi.repository.RoleRepository;
//import com.fyp.erpapi.erpapi.service.RoleService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//
//@Component
//@AllArgsConstructor
//public class InitializeData {
//
//    private AuthorityRepository authorityRepository;
//    private RoleRepository roleRepository;
//    private RoleService roleService;
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void loadAuthorities() {
//        Authority moveToOnboardingB = new Authority("MOVE_TO_ONBOARDING_B");
//        Authority moveToOnboardingC = new Authority("MOVE_TO_ONBOARDING_C");
//        Authority onboard = new Authority("ONBOARD");
//        Authority admin = new Authority("ADMIN");
//        Authority user = new Authority("USER");
//        Authority manager = new Authority("MANAGER");
//        this.authorityRepository.save(moveToOnboardingB);
//        this.authorityRepository.save(moveToOnboardingC);
//        this.authorityRepository.save(onboard);
//        this.authorityRepository.save(admin);
//        this.authorityRepository.save(user);
//        this.authorityRepository.save(manager);
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void loadRoles() {
//        Role admin = new Role("ADMIN");
//        Role user = new Role("USER");
//        Role manager = new Role("MANAGER");
//
//        Role nonOnboardedUserA = new Role("NON_ONBOARDED_USER_A");
//        Role nonOnboardedUserB = new Role("NON_ONBOARDED_USER_B");
//        Role nonOnboardedUserC = new Role("NON_ONBOARDED_USER_C");
//
//        admin.addAuthority(this.authorityRepository.findByName("MOVE_TO_ONBOARDING_B").get());
//        admin.addAuthority(this.authorityRepository.findByName("MOVE_TO_ONBOARDING_C").get());
//        admin.addAuthority(this.authorityRepository.findByName("ONBOARD").get());
//        admin.addAuthority(this.authorityRepository.findByName("ADMIN").get());
//        user.addAuthority(this.authorityRepository.findByName("USER").get());
//        manager.addAuthority(this.authorityRepository.findByName("MANAGER").get());
//        nonOnboardedUserA.addAuthority(this.authorityRepository.findByName("MOVE_TO_ONBOARDING_B").get());
//        nonOnboardedUserB.addAuthority(this.authorityRepository.findByName("MOVE_TO_ONBOARDING_C").get());
//        nonOnboardedUserC.addAuthority(this.authorityRepository.findByName("ONBOARD").get());
//
//        this.roleRepository.save(admin);
//        this.roleRepository.save(user);
//        this.roleRepository.save(manager);
//        this.roleRepository.save(nonOnboardedUserA);
//        this.roleRepository.save(nonOnboardedUserB);
//        this.roleRepository.save(nonOnboardedUserC);
//    }
//}
