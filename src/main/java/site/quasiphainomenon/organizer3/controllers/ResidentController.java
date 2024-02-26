package site.quasiphainomenon.organizer3.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import site.quasiphainomenon.organizer3.domains.Resident;
import site.quasiphainomenon.organizer3.repositories.ResidentRepository;

@Controller
public class ResidentController {

    @Autowired
    ResidentRepository residentRepository;

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/newresident")
    public String showAddResidentForm(Resident resident) {

        return "add-resident";
    }

    @PostMapping("/addresident")
    public String addResident(@Valid Resident resident, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-resident";
        }

        residentRepository.save(resident);
        return "redirect:/edit-list";
    }

    @GetMapping("/edit-list")
    public String showResidentList(Model model) {
        model.addAttribute("residents", residentRepository.findByOrderByLastnameAsc());
        return "edit-list";
    }

    @GetMapping("/view-list")
    public String showViewAddList(Model model) {
        model.addAttribute("residents", residentRepository.findByOrderByLastnameAsc());
        return "view-list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resident Id:" + id));

        model.addAttribute("resident", resident);
        return "update-resident";
    }

    @PostMapping("/update/{id}")
    public String updateResident(@PathVariable("id") long id, @Valid Resident resident,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            resident.setId(id);
            return "update-resident";
        }

        residentRepository.save(resident);
        return "redirect:/edit-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteResident(@PathVariable("id") long id, Model model) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resident Id:" + id));
        residentRepository.delete(resident);
        return "redirect:/edit-list";
    }
}