package com.Project.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.Project.core.services.PracticeService;

@Component(service = PracticeService.class)
public class PracticeServiceImpl implements PracticeService {

    @Override
    public String getName() {
        return "John from PracticeServiceImpl";
    }

}
