package com.aurora.pos.entidades;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;

public class FacturaCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        ((AggregateObjectMapping)classDescriptor.getMappingForAttributeName("estadoEmision")).setIsNullAllowed(false);
    }
}
