package com.Project.core.models;

import java.util.ArrayList;
import java.util.*;
// import com.adobe.acs.commons.genericlists.GenericList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class)
public class Project {

    @SlingObject
    private ResourceResolver resourceResolver;

    List<Item> countries = new ArrayList<>();

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page listPage = pageManager.getPage("/etc/acs-commons/lists/countries");
        // GenericServlet list = listPage.adaptTo(GenericList.class);
        // for (GenericList.Item item : list.getItems()) {
        // countries.add(new Item(item.getTitle(), item.getValue()));
        // }
    }

    public List<Item> getCountries() {
        return countries;
    }

    public void setCountries(List<Item> countries) {
        this.countries = countries;
    }

}
