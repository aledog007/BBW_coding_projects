package ch.clip.bugtracker.service;

import ch.clip.bugtracker.domain.Application;
import ch.clip.bugtracker.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> listApplications() {
        return applicationRepository.findAll();
    }

}
