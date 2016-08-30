package sk.softec.ga.module.services;

import org.jboss.as.server.ServerEnvironment;
import org.jboss.msc.inject.Injector;
import org.jboss.msc.service.*;
import org.jboss.msc.value.InjectedValue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class HATimerService implements Service<String> {

	private static final Logger log = Logger.getLogger(HATimerService.class.getCanonicalName());

	public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("gamodule", "scheduler", "singleton");

	/**
	 * A flag whether the service is started.
	 */
	private final AtomicBoolean started = new AtomicBoolean(false);

	private String nodeName;

	private final InjectedValue<ServerEnvironment> env = new InjectedValue<ServerEnvironment>();

	public Injector<ServerEnvironment> getEnvInjector() {
		return this.env;
	}

	public boolean isMaster() {
		return started.get();
	}
	
	public String getValue() throws IllegalStateException,
			IllegalArgumentException {
		if (!started.get()) {
			throw new IllegalStateException("The service '"
					+ this.getClass().getName() + "' is not ready!");
		}
		return this.nodeName;
	}

	public void start(StartContext arg0) throws StartException {
		if (!started.compareAndSet(false, true)) {
			throw new StartException("The service is still started!");
		}
		log.info("Start service '" + this.getClass().getName() + "'");
		this.nodeName = this.env.getValue().getNodeName();
	}

	public void stop(StopContext arg0) {
		if (!started.compareAndSet(true, false)) {
			log.warning("The service '" + this.getClass().getName()
					+ "' is not active!");
		} else {
			log.info("Stop service '" + this.getClass().getName() + "'");
		}
	}
}
