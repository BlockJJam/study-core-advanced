package hello.advanced.trace;

import java.util.UUID;

public class TraceId {
    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }

    private String createId(){
        // 생성된 UUID가 너무 길고 중복이 될 수 있지만, 어느 정도의 중복은 상관없다고 판단해서 8자리만 사용
        return UUID.randomUUID().toString().substring(0,8);
    }

    // 다음 메서드 호출 깊이로 넘어가서 로그를 쌓을 때 필요한 ID를 (id는 갖고, level은 다른) 만들어주는 메서드
    private TraceId createNextId(){
        return new TraceId(id, level+1);
    }

    private TraceId createPreviousId(){
        return new TraceId(id, level -1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
