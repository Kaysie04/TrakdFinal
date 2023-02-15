package com.anderson.trakd.controller;
import com.anderson.trakd.model.Dept;
import com.anderson.trakd.model.Manager;
import com.anderson.trakd.model.NHPersonalInformation;
import com.anderson.trakd.repository.DeptRepository;
import com.anderson.trakd.repository.ManagerRepository;
import com.anderson.trakd.repository.NHCompanyCredentialsRepository;
import com.anderson.trakd.repository.NHPersonalInformationRepository;
import com.anderson.trakd.service.NHCompanyCredentialsService;
import com.anderson.trakd.service.NHPersonalInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class NHPersonalInformationController {


    private final NHPersonalInformationService nhPersonalInformationService;

    private final NHCompanyCredentialsService nhCompanyCredentialsService;
    private final DeptRepository deptRepository;
    private final ManagerRepository managerRepository;

    private final NHCompanyCredentialsRepository nhCompanyCredentialsRepository;
    @Autowired
    private NHPersonalInformationRepository nhPersonalInformationRepository;

    public NHPersonalInformationController(NHPersonalInformationService nhPersonalInformationService,
                                           NHCompanyCredentialsService nhCompanyCredentialsService,
                                           DeptRepository deptRepository,
                                           ManagerRepository managerRepository,
                                           NHCompanyCredentialsRepository nhCompanyCredentialsRepository)
    {
        this.nhPersonalInformationService = nhPersonalInformationService;
        this.nhCompanyCredentialsService = nhCompanyCredentialsService;
        this.deptRepository = deptRepository;
        this.managerRepository = managerRepository;
        this.nhCompanyCredentialsRepository = nhCompanyCredentialsRepository;
    }

    @GetMapping("/all-newhires-personal")
    public String getAllNewHiresPersonal(Model model) {
        model.addAttribute("allNewhires", nhPersonalInformationService.getAllNHPersonal());
        nhPersonalInformationService.getAllNHPersonal();
        return "all-newhires-personal";
    }


    @GetMapping("/create-newhire")
    public String renderCreateNewHire(Model model){
        model.addAttribute("nhPersonal", new NHPersonalInformation());
        model.addAttribute("dept", deptRepository.findAll());
        model.addAttribute("manager", managerRepository.findAll());
        model.addAttribute("companyCredentials", nhCompanyCredentialsService.getAllNHCredentials());
        return "create-newhire";
    }


    @PostMapping("/create-newhire")
    public String createNewHire(@ModelAttribute("nhPersonal") NHPersonalInformation nhPersonalInformation) {
        nhPersonalInformationService.createNHPersonal(nhPersonalInformation);
//        return "redirect:/employee";
        return"success";
    }

    @GetMapping("/newhires-by-dept")
    public String getAllNHByDeptId(@RequestParam("deptId") Long deptId, Model model){
        List<NHPersonalInformation> nhPersonalList = nhPersonalInformationService.getNHPersonalByDeptId(deptId);
        Dept deptName = deptRepository.getReferenceById(deptId);
        model.addAttribute("newhires", nhPersonalList );
        model.addAttribute("dept", deptName);
        return "all-newhires-by-dept";
    }

    @GetMapping("/newhires-by-manager")
    public String getAllNHByManagerId(@RequestParam("managerId") Long managerId, Model model){
        List<NHPersonalInformation> nhPersonalList = nhPersonalInformationService.getNHPersonalByManagerId(managerId);
        Manager managerName = managerRepository.getReferenceById(managerId);
        model.addAttribute("newhires", nhPersonalList );
        model.addAttribute("manager", managerName);
        return "all-newhires-by-manager";
    }

    @GetMapping("/newhire-by-id-company")
    public String getNHById(@RequestParam("nhId") Long nhId, Model model){
        NHPersonalInformation nhPersonal = nhPersonalInformationService.getNHPersonalById(nhId);
        model.addAttribute("newhire", nhPersonal );
        return "newhire-by-id";
    }

}












