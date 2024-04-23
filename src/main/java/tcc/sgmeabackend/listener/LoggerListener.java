package tcc.sgmeabackend.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;

public class LoggerListener implements ApplicationListener<AbstractAuthorizationEvent> {

    private static final Log logger = LogFactory.getLog(LoggerListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        logger.info("Evento de falha de autorização capturado: " + event);
    }
}
