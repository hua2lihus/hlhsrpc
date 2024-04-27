package com.hlhs.hlhsrpc.fault.retry;

import com.github.rholder.retry.*;
import com.hlhs.hlhsrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 固定时间间隔重试策略
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {

        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                //设置重试条件
                .retryIfExceptionOfType(Exception.class)
                //设置重试等待策略fixedWait（固定时间）
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                //设置停止策略stopAfterAttempt（超过最大重试次数停止）
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //重试工作
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }
}
