package sk.softec.ga.crm.rest;

import org.glassfish.jersey.server.ResourceConfig;
import sk.softec.ga.crm.filter.CORSResponseFilter;
import sk.softec.ga.crm.rest.serconfig.JacksonMapperProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends ResourceConfig {

    private Set<Class<?>> classes;

    public RestApplication() {
        super();
        initClasses();
        registerClasses(classes);
    }

    private void initClasses() {
        System.out.println("Application configured ...");
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.addAll(getProviders());
        classes.addAll(getRootResources());
        this.classes = Collections.unmodifiableSet(classes);
    }

    public Set<Class<?>> getProviders() {
        return new HashSet<Class<?>>(Arrays.<Class<?>>asList(
                JacksonMapperProvider.class,
                CORSResponseFilter.class
        ));
    }

    public Set<Class<?>> getRootResources() {
        return new HashSet<Class<?>>(Arrays.<Class<?>>asList(
                CRMResource.class
        ));
    }

}
