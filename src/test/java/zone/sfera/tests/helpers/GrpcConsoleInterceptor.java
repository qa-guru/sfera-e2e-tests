package zone.sfera.tests.helpers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class GrpcConsoleInterceptor implements ClientInterceptor {
    private static final Logger log = LoggerFactory.getLogger(GrpcConsoleInterceptor.class);
    private static final JsonFormat.Printer jsonPrinter = JsonFormat.printer();

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                channel.newCall(methodDescriptor, callOptions.withoutWaitForReady())) {
            private final Set<String> parsedResponses = new HashSet<>();

            @Override
            public void sendMessage(ReqT message) {
                log.info("Send gRPC request to: " + methodDescriptor.getFullMethodName());
                try {
                    log.info("Request body:\n\n" + jsonPrinter.print((MessageOrBuilder) message));
                } catch (InvalidProtocolBufferException e) {
                    log.warn("Can`t parse gRPC request", e);
                }
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                ClientCall.Listener<RespT> listener = new ForwardingClientCallListener<RespT>() {
                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        log.info("Receive gRPC responses:\n\n" + parsedResponses);
                        super.onClose(status, trailers);
                    }

                    @Override
                    protected Listener<RespT> delegate() {
                        return responseListener;
                    }

                    @Override
                    public void onMessage(RespT message) {
                        try {
                            parsedResponses.add(jsonPrinter.print((MessageOrBuilder) message));
                        } catch (InvalidProtocolBufferException e) {
                            log.warn("Can`t parse gRPC response", e);
                        }
                        super.onMessage(message);
                    }
                };
                super.start(listener, headers);
            }
        };
    }
}