package net.noneuclideangirl.requests;

import net.noneuclideangirl.ServiceMonitor;
import net.noneuclideangirl.net.AbstractRequestHandler;
import net.noneuclideangirl.net.Response;
import net.noneuclideangirl.DatabaseManager;
import org.bson.Document;

public class ServiceLogsRequestHandler extends AbstractRequestHandler {
    public static final String COMMAND_NAME = "SERVICE_LOGS";

    public ServiceLogsRequestHandler() {
        super(COMMAND_NAME);
    }

    @Override
    protected Document process(Document doc) {
        return DatabaseManager.get().findServiceByIdOrName(doc)
                .andThen(ServiceMonitor::getRecentLogs)
                .matchThen(out -> Response.ok().append("data", out),
                           Response::err);
    }
}
