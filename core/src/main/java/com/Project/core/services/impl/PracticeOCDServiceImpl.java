package com.Project.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.Project.core.services.PracticeOCDService;
import com.Project.core.services.PracticeServiceOCDConfiguration;

@Component(service = PracticeOCDService.class, immediate = true)
@Designate(ocd = PracticeServiceOCDConfiguration.class)
public class PracticeOCDServiceImpl implements PracticeOCDService {

    private String name;
    private String[] supportedCountries;

    @Activate
    protected void activate(PracticeServiceOCDConfiguration config) {
        this.name = config.name();
        this.supportedCountries = config.supported_countries();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getCountries() {
        return supportedCountries;
    }
}
