package cz.hartrik.sg2.app;

import cz.hartrik.common.Exceptions;
import cz.hartrik.sg2.app.module.ApplicationCompoundModule;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import cz.hartrik.sg2.app.service.ServiceProvider;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Stará se o postavení aplikace z modulů.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ApplicationBuilder {

    private ApplicationBuilder() {}

    public static Application build(ApplicationModule[] modules) {
        Frame frame = new Frame();

        Application app = new Application(frame, frame.getFrameController());
        ServiceManager serviceManager = app.getServiceManager();

        Stack<Object> stack = initModules(app, modules);
        Set<Class<?>> requiredProviders = walk(serviceManager, stack);

        Exceptions.unchecked(() -> {
            loadRequiredServiceProviders(requiredProviders, serviceManager);
        });

        return app;
    }

    private static Stack<Object> initModules(
            Application app, ApplicationModule[] modules) {

        Stack<Object> stack = new Stack<>();

        for (ApplicationModule applicationModule : modules) {
            applicationModule.init(app);
            stack.push(applicationModule);
        }
        return stack;
    }

    private static Set<Class<?>> walk(
            ServiceManager serviceManager, Stack<Object> stack) {

        Set<Class<?>> requiredProviders = new LinkedHashSet<>();
        while (!stack.isEmpty()) {
            Object object = stack.pop();

            Require[] annotations = object.getClass().getAnnotationsByType(Require.class);
            for (Require annotation : annotations) {
                requiredProviders.add(annotation.value());
            }

            if (object.getClass().isAnnotationPresent(ServiceProvider.class)) {
                serviceManager.register(object);
            }

            if (object instanceof ApplicationCompoundModule) {
                ApplicationCompoundModule acm = (ApplicationCompoundModule) object;
                Object[] subModules = acm.getSubModules();
                for (Object subModule : subModules) {
                    stack.add(subModule);
                }
            }
        }
        return requiredProviders;
    }

    private static void loadRequiredServiceProviders(
            Set<Class<?>> requiredProviders, ServiceManager serviceManager)
            throws InstantiationException, IllegalAccessException {

        for (Class<?> provider : requiredProviders) {
            Object newInstance = provider.newInstance();
            serviceManager.register(newInstance);
        }
    }

}