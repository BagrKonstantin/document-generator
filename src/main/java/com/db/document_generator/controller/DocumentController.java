package com.db.document_generator.controller;

import com.db.document_generator.domain.Classes;
import com.db.document_generator.domain.Department;
import com.db.document_generator.domain.DocumentTypes;
import com.db.document_generator.domain.Person;
import com.db.document_generator.domain.User;
import com.db.document_generator.model.DocumentDTO;
import com.db.document_generator.repos.ClassesRepository;
import com.db.document_generator.repos.DepartmentRepository;
import com.db.document_generator.repos.DocumentTypesRepository;
import com.db.document_generator.repos.PersonRepository;
import com.db.document_generator.repos.UserRepository;
import com.db.document_generator.service.DocumentService;
import com.db.document_generator.util.CustomCollectors;
import com.db.document_generator.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentTypesRepository documentTypesRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final ClassesRepository classesRepository;

    public DocumentController(final DocumentService documentService,
            final DocumentTypesRepository documentTypesRepository,
            final UserRepository userRepository, final PersonRepository personRepository,
            final DepartmentRepository departmentRepository,
            final ClassesRepository classesRepository) {
        this.documentService = documentService;
        this.documentTypesRepository = documentTypesRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.departmentRepository = departmentRepository;
        this.classesRepository = classesRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("documentTypeValues", documentTypesRepository.findAll(Sort.by("documentTypeId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(DocumentTypes::getDocumentTypeId, DocumentTypes::getTittle)));
        model.addAttribute("userValues", userRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getUserId, User::getFirstname)));
        model.addAttribute("teacherValues", personRepository.findAll(Sort.by("personId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Person::getPersonId, Person::getFirstname)));
        model.addAttribute("headTeacherValues", personRepository.findAll(Sort.by("personId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Person::getPersonId, Person::getFirstname)));
        model.addAttribute("departmentValues", departmentRepository.findAll(Sort.by("departmentId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Department::getDepartmentId, Department::getTitle)));
        model.addAttribute("classsValues", classesRepository.findAll(Sort.by("classId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Classes::getClassId, Classes::getTitle)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("documents", documentService.findAll());
        return "document/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("document") final DocumentDTO documentDTO) {
        return "document/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("document") @Valid final DocumentDTO documentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "document/add";
        }
        documentService.create(documentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("document.create.success"));
        return "redirect:/documents";
    }

    @GetMapping("/edit/{documentId}")
    public String edit(@PathVariable(name = "documentId") final Long documentId,
            final Model model) {
        model.addAttribute("document", documentService.get(documentId));
        return "document/edit";
    }

    @PostMapping("/edit/{documentId}")
    public String edit(@PathVariable(name = "documentId") final Long documentId,
            @ModelAttribute("document") @Valid final DocumentDTO documentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "document/edit";
        }
        documentService.update(documentId, documentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("document.update.success"));
        return "redirect:/documents";
    }

    @PostMapping("/delete/{documentId}")
    public String delete(@PathVariable(name = "documentId") final Long documentId,
            final RedirectAttributes redirectAttributes) {
        documentService.delete(documentId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("document.delete.success"));
        return "redirect:/documents";
    }

}
