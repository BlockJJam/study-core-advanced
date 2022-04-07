package hello.advanced.trace;

// 로그를 시작하면 끝도 있어야 한다 <- TraceID, 로그를 시작했을 때의 메시지가 끝에도 알아야하고, 시간을 항상 체크해야 한다
public class TraceStatus {
    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
