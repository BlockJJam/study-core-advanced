package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    /**
     * Strategy 패턴 적용 - logic1
     */
    @Test
    void strategyV1_logic1(){
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);

        contextV1.excute();
    }

    /**
     * Strategy 패턴 적용 - logic2
     */
    @Test
    void strategyV1_logic2(){
        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV1 = new ContextV1(strategyLogic2);

        contextV1.excute();
    }

    /**
     * 익명 클래스 활용한 방법
     */
    @Test
    void strategyV2(){
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("익명의 비즈니스 로직 1실행");
            }
        };

        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.excute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("익명의 비즈니스 로직2 실행");
            }
        };

        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.excute();
    }

    /**
     * 익명 클래스를 인라인으로 합쳐서 간소화한 버전
     */
    @Test
    void strategyV3(){
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("익명의 비즈니스 로직 1실행");
            }
        });
        context1.excute();


        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("익명의 비즈니스 로직2 실행");
            }
        });
        context2.excute();
    }

    /**
     * 람다를 활용한 전략패턴 테스트 코드 - 메서드가 1개일 경우에만
     */
    @Test
    void strategyV4(){
        ContextV1 context1 = new ContextV1(() -> log.info("익명의 비즈니스 로직 1실행"));
        context1.excute();


        ContextV1 context2 = new ContextV1(() -> log.info("익명의 비즈니스 로직2 실행"));
        context2.excute();
    }

}
