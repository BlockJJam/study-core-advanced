package hello.advanced.trace.HelloTrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // 메서드의 도입부분 로그
    public TraceStatus begin(String message){
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        // 로그 출력
        return new TraceStatus(traceId, startTimeMs, message);
    }

    // V2에 추가된 부분 - TraceID를 받아보자
    public TraceStatus beginSync(String message, TraceId beforeTraceId){
        TraceId nextId = beforeTraceId.createNextId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        // 로그 출력
        return new TraceStatus(nextId, startTimeMs, message);
    }

    // 메서드의 탈출부분 로그
    public void end(TraceStatus status){
        complete(status, null);
    }

    // 메서드에서 예외가 발생했을 때의 로그
    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }

    // end와 exception 로그가 Exception 여부만 다르고 공통적으로 처리하는 로직을 가지고 있기 때문에 중복 코드를 분리
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if( e == null){
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        }else{
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,e.toString());
        }

    }

    // level 0:
    // level 1: |-->
    // level 2: |   |-->
    // level 2, ex: |   |<X-
    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
