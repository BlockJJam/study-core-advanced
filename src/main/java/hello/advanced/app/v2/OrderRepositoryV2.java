package hello.advanced.app.v2;

import hello.advanced.trace.HelloTrace.HelloTraceV2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(TraceId traceId, String itemId){
        TraceStatus status = null;
        try{
            status = trace.beginSync("OrderRepository.save()", traceId);

            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalArgumentException("예외발생");
            }
            sleep(1000);
            trace.end(status);
        }catch(Exception e){
            trace.exception(status, e);
            throw e;
        }

    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
