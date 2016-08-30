package sk.softec.ga.module.services;

import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.EnumSet;

@Service
public class GAModuleCntxClosedAppListener implements ApplicationListener<ContextClosedEvent> {

	private static final Logger log = LoggerFactory
			.getLogger(GAModuleCntxClosedAppListener.class);

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("StartupSingleton will be removed!");

		ServiceContainer container = CurrentServiceContainer.getServiceContainer();

		if (container == null) {
			log.error("JBoss EAP returned ServiceContainer as null. Singleton will not be removed correctly!");
			return;
		}

		ServiceController<?> controller = container.getRequiredService(HATimerService.SINGLETON_SERVICE_NAME);
		controller.setMode(ServiceController.Mode.REMOVE);
		try {
			wait(controller, EnumSet.of(ServiceController.State.UP,
							ServiceController.State.STOPPING,
							ServiceController.State.DOWN),
					ServiceController.State.REMOVED);
			log.debug("StartupSingleton has stopped the Service");
		} catch (IllegalStateException e) {
			log.warn("Singleton Service "
					+ HATimerService.SINGLETON_SERVICE_NAME
					+ " has not be stopped correctly!");
		}

	}
	
	private static <T> void wait(ServiceController<T> controller, Collection<ServiceController.State> expectedStates, ServiceController.State targetState) {
		if (controller.getState() != targetState) {
			ServiceListener<T> listener = new GAModuleCntxRefreshedAppListener.NotifyingServiceListener<T>();
			controller.addListener(listener);
			try {
				synchronized (controller) {
					int maxRetry = 3;
					while (expectedStates.contains(controller.getState()) && maxRetry > 0) {
						log.info("Service controller state is " + controller.getState() + ", waiting for transition to " + targetState);
						controller.wait(5000);
						maxRetry--;
					}
				}
			} catch (InterruptedException e) {
				log.warn("Wait on startup is interrupted!");
				Thread.currentThread().interrupt();
			}
			controller.removeListener(listener);
			ServiceController.State state = controller.getState();
			log.info("Service controller state is now " + state);
			if (state != targetState) {
				throw new IllegalStateException(String.format("Failed to wait for state to transition to %s.  Current state is %s", targetState, state), controller.getStartException());
			}
		}
	}
}
