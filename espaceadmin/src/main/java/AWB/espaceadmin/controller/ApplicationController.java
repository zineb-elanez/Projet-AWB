package AWB.espaceadmin.controller;

import AWB.espaceadmin.model.Application;
import AWB.espaceadmin.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/{id}")
    public List<Application> getAllApplicationUsers(@PathVariable Long id) {
        return applicationService.getAllApplications();
    }
    @GetMapping()
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @PostMapping()
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id, @RequestBody Application application) {
        return applicationService.updateApplication(id, application);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }
}