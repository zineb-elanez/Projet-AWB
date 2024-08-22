package AWB.espaceadmin.Service;

import AWB.espaceadmin.model.Application;
import AWB.espaceadmin.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    public List<Application> getAllApplicationUsers() {
        return applicationRepository.findAll();
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public Application updateApplication(Long id, Application application) {
        if (applicationRepository.existsById(id)) {
            application.setId(id);
            return applicationRepository.save(application);
        }
        return null;
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}