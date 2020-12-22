package com.laurentiuspilca.ssia.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(final Authentication a) {
//        SecurityContext context = SecurityContextHolder.getContext();
//        return String.format("Hello %s!", context.getAuthentication().getName());
        return String.format("Hello %s!", a.getName());
    }

    @GetMapping("/bye")
    @Async
    public String goodbye() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            System.out.println(String.format("Bye %s!", "man"));
            return String.format("Bye %s!", "man");
        } else {
            String username = context.getAuthentication().getName();
            System.out.println(String.format("Bye %s!", username));
            return String.format("Bye %s!", username);
        }
    }

    @GetMapping("callable")
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("start task");
            final SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };

//        return task.call();

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            return executorService.submit(task).get();
        } finally {
            executorService.shutdown();
        }
    }
}
