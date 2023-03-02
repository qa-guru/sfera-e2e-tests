package zone.sfera.tests.tests.grpc;

import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import zona.sfera.ui.ChatGrpc;
import zona.sfera.ui.MessangerProto;
import zone.sfera.tests.config.Credentials;
import zone.sfera.tests.helpers.GrpcConsoleInterceptor;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class BaseGrpcTest {
    protected static final String token = Credentials.sferaId.grpcToken();
    protected static final ChatGrpc.ChatStub chatStub;
    protected static final ChatGrpc.ChatBlockingStub chatBlockingStub;

    static {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("authorization", ASCII_STRING_MARSHALLER), token);
        chatStub = ChatGrpc.newStub(
                ManagedChannelBuilder.forAddress(Credentials.envConfig.baseGrpcURI(), Credentials.envConfig.baseGrpcPort())
                        .intercept(MetadataUtils.newAttachHeadersInterceptor(metadata), new GrpcConsoleInterceptor())
                        .usePlaintext()
                        .build()
        );
        chatBlockingStub = ChatGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress(Credentials.envConfig.baseGrpcURI(), Credentials.envConfig.baseGrpcPort())
                        .intercept(MetadataUtils.newAttachHeadersInterceptor(metadata), new GrpcConsoleInterceptor())
                        .usePlaintext()
                        .build()
        );

    }

    @Step("Отправить сообщения через stream")
    protected Map<String, MessangerProto.StreamResponse> sendMessagesWithStream(Queue<MessangerProto.StreamRequest> defaultReq, Logger log) throws InterruptedException {
        Map<String, MessangerProto.StreamResponse> responseMap = new ConcurrentHashMap<>();
        AtomicReference<Throwable> serverError = new AtomicReference<>();
        CountDownLatch finishedLatch = new CountDownLatch(1);
        StreamObserver<MessangerProto.StreamResponse> sendMessageResponseObserver = new StreamObserver<>() {
            @Override
            public void onNext(MessangerProto.StreamResponse response) {
                if (response.hasError()) {
                    responseMap.put(response.getError().getItem().getOID(), response);
                } else {
                    responseMap.put(response.getMessage().getOID(), response);
                }
                finishedLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                log.info("on error: \n");
                log.error(t.toString());
                serverError.set(t);
                finishedLatch.countDown();
            }

            @Override
            public void onCompleted() {
                log.info("on completed: \n");
                finishedLatch.countDown();
            }
        };

        StreamObserver<MessangerProto.StreamRequest> observer = chatStub.withDeadlineAfter(30, TimeUnit.SECONDS)
                .stream(sendMessageResponseObserver);
        while (!defaultReq.isEmpty()) {
            observer.onNext(defaultReq.remove());
            Thread.sleep(1000L);
        }

        finishedLatch.await();
        observer.onCompleted();

        Throwable throwable = serverError.get();
        if (throwable != null) {
            Assertions.fail(throwable);
        }
        return responseMap;
    }
}
