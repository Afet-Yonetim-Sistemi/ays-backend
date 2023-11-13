package com.ays.institution.model.entity;

import com.ays.common.model.TestDataBuilder;
import com.ays.common.util.AysRandomUtil;
import com.ays.institution.model.Institution;

public class InstitutionBuilder extends TestDataBuilder<Institution> {

    public InstitutionBuilder() {
        super(Institution.class);
    }

    public InstitutionBuilder withValidFields() {
        return this
                .withId(AysRandomUtil.generateUUID())
                .withName("Test Institution " + AysRandomUtil.generateUUID());
    }

    public InstitutionBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public InstitutionBuilder withName(String name) {
        data.setName(name);
        return this;
    }

}
