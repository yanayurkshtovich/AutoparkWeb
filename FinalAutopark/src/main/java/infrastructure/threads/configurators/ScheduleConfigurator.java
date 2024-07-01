package infrastructure.threads.configurators;

import infrastructure.configurators.impl.ProxyConfigurator;
import infrastructure.core.impl.Context;
import infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {
    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) throws Exception {
        for (Method m : object.getClass().getMethods()) {
            if (m.isAnnotationPresent(Schedule.class)) {
                if (m.getReturnType().getSimpleName().equals("void") || !isOnlyPublicMethodModifier(m)) {
                    throw new Exception("Method returns void or not public");
                }
                else {
                    return (T) Enhancer.create(implementation, (MethodInterceptor) this::invoke);
                }
            }
        }

        return object;
    }

    @SneakyThrows
    private boolean isOnlyPublicMethodModifier(Method method) {
        return Modifier.toString(method.getModifiers()).equals("public");
    }

    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule scheduleSync = method.getAnnotation(Schedule.class);
        if (scheduleSync != null) {
            System.out.println(method);
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, scheduleSync.timeout(), scheduleSync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }

        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object object, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while(true) {
                try {
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
                            @Override
                            public Thread newThread(@NotNull Runnable r) {
                                Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                fThread.setDaemon(true);
                                return fThread;
                            }
                        });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(object, args);
                                } catch (Throwable throwable) {
                                    throwable.getStackTrace();
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (Exception e) {
                            executorService.shutdownNow();
                        }
                        executorService.shutdown();
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.currentThread().sleep(delta);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
