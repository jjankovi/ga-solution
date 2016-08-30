package sk.softec.ga.module.services;

import org.apache.commons.lang3.time.DateUtils;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.as.server.ServerEnvironmentService;
import org.jboss.msc.service.AbstractServiceListener;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceController.Transition;
import org.jboss.msc.service.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.services.scheduler.ModuleScheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

@Service
public class GAModuleCntxRefreshedAppListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(GAModuleCntxRefreshedAppListener.class);

	@Autowired
	private ModuleScheduler scheduler;
	
	private HATimerService singletonService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("StartupSingleton will be initialized!");

		if (runningOnJBoss()) {
			log.info("Waiting for JBoss HA Singleton Service...");
			this.singletonService = waitForJBossHaSingletonService();
		} else {
			log.warn("Running on non-JBoss server, singleton service will not be available, scheduler jobs will run on every cluster node.");
			createDummySingletonService();
			this.singletonService = createDummySingletonService();
		}

		log.info("Starting schedulers ...");
		try {
			scheduler.scheduleCRMEventRead(DateUtils.addSeconds(new Date(), 30));
		} catch (Exception e) {
			log.error("Error starting scheduler",e);
		}
	}

	public boolean isMaster() {
		return singletonService.isMaster();
	}

	private boolean runningOnJBoss() {
		return CurrentServiceContainer.getServiceContainer() != null;
	}

	private HATimerService waitForJBossHaSingletonService() {
		ServiceContainer container = CurrentServiceContainer.getServiceContainer();

		HATimerService singletonService = new HATimerService();
		ServiceController<String> controller = container.addService(HATimerService.SINGLETON_SERVICE_NAME, singletonService)
			.addDependency(ServerEnvironmentService.SERVICE_NAME, ServerEnvironment.class, singletonService.getEnvInjector())
			.install();
		
		controller.setMode(ServiceController.Mode.ACTIVE);
		try {
			wait(controller, EnumSet.of(ServiceController.State.DOWN, ServiceController.State.STARTING), ServiceController.State.UP);
			log.debug("StartupSingleton has started the Service");
		} catch (IllegalStateException e) {
			log.warn("Singleton Service " + HATimerService.SINGLETON_SERVICE_NAME + " not started, are you sure to start in a cluster (HA) environment?");
		}

		return singletonService;
	}

	private HATimerService createDummySingletonService() {
		return new HATimerService() {
			@Override
			public boolean isMaster() {
				return true;
			}

			@Override
			public String getValue() throws IllegalStateException, IllegalArgumentException {
				try {
					return InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}

	private static <T> void wait(ServiceController<T> controller, Collection<ServiceController.State> expectedStates, ServiceController.State targetState) {
		if (controller.getState() != targetState) {
			ServiceListener<T> listener = new NotifyingServiceListener<T>();
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
	public static class NotifyingServiceListener<T> extends AbstractServiceListener<T> {
		@Override
	    public void transition(ServiceController<? extends T> controller, Transition transition) {
			synchronized (controller) {
				controller.notify();
			}
		}

	}
}
