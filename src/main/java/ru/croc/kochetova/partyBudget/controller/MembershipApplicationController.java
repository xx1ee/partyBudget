package ru.croc.kochetova.partyBudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.croc.kochetova.partyBudget.model.MembershipApplication;
import ru.croc.kochetova.partyBudget.model.Status;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.service.MembershipApplicationService;
import ru.croc.kochetova.partyBudget.service.RoleService;
import ru.croc.kochetova.partyBudget.service.UserDetailService;

import java.util.List;

@Controller
public class MembershipApplicationController {
    private final MembershipApplicationService membershipApplicationService;
    private final UserDetailService userDetailService;
    private final RoleService roleService;
    @Autowired
    public MembershipApplicationController(MembershipApplicationService membershipApplicationService,
                                           UserDetailService userDetailService, RoleService roleService) {
        this.membershipApplicationService = membershipApplicationService;
        this.userDetailService = userDetailService;
        this.roleService = roleService;
    }
    @GetMapping("/membershipApplication")
    public String handleMembershipPage() {
        return "membership_application";
    }

    @PostMapping("/membershipApplication")
    public String handleMembershipApp() {
        membershipApplicationService.saveApplication(SecurityContextHolder.getContext().getAuthentication().getName());
        return "home_user";
    }
    @GetMapping("/requests")
    public String showRequests(Model model) {
        List<MembershipApplication> requests = membershipApplicationService.findAllUnfinishedApplications();
        model.addAttribute("requests", requests);
        return "requests_membership";
    }
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Integer id, @RequestParam String status) {
        membershipApplicationService.updateStatus(id, Status.valueOf(status));
        return "redirect:/requests";
    }
    @GetMapping("/members")
    public String showMembers(Model model) {
        List<User> requests = userDetailService.findAllByAuthority(roleService.findFirstByAuthority("PARTY_MEMBER"));
        model.addAttribute("requests", requests);
        return "all_party_members";
    }
    @PostMapping("/deleteMember")
    public String deleteMembers(@RequestParam Integer id) {
        membershipApplicationService.deleteMember(id);
        return "redirect:/members";
    }
}
