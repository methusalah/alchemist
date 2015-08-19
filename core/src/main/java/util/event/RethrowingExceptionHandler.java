package util.event;

import util.exception.TechnicalException;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Simple logging handler for subscriber exceptions.
 */
class RethrowingExceptionHandler implements SubscriberExceptionHandler {


	@Override
	public void handleException(Throwable exception, SubscriberExceptionContext context) {
		throw new TechnicalException(exception);
	}
}