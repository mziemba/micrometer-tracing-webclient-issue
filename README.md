# micrometer-tracing-webclient-issue

To run the service:
```bash
./gradlew bootRun
```

To reproduce the issue:
```
http -v http://localhost:8080/demo
```

The issue is: tracing information is lost when switching to reactor-http threads when using WebClient

```
[INFO] [2023-06-02 22:28:24,158] [demo-service,647a50e8a0a448a9d729afe03e174ee9,d729afe03e174ee9,demo-client] [qtp2118413714-24] d.DemoController: Starting to process request in controller
[INFO] [2023-06-02 22:28:24,165] [demo-service,647a50e8a0a448a9d729afe03e174ee9,d729afe03e174ee9,demo-client] [qtp2118413714-24] d.DemoController: Finishing to process request in controller
[INFO] [2023-06-02 22:28:27,279] [demo-service,,,] [reactor-http-nio-2] d.DemoController: This log is missing tracing information!!!
[INFO] [2023-06-02 22:28:27,279] [demo-service,,,] [reactor-http-nio-2] d.DemoController: This log is missing tracing information!!!
```

After adding `Hooks.enableAutomaticContextPropagation()` trace and span are propagated, but baggage is not:

```
[INFO] [2023-08-14 17:47:17,599] [demo-service,999927cdec71191383f28bee90fc0658,208337a4de174dbf,demo-client,efbbd7b1-cfa4-4572-b988-d95a21728d2f] [qtp627519623-24] d.DemoController: Starting to process request in controller
[INFO] [2023-08-14 17:47:17,609] [demo-service,999927cdec71191383f28bee90fc0658,208337a4de174dbf,demo-client,efbbd7b1-cfa4-4572-b988-d95a21728d2f] [qtp627519623-24] d.DemoController: Finishing to process request in controller
[INFO] [2023-08-14 17:47:20,856] [demo-service,999927cdec71191383f28bee90fc0658,208337a4de174dbf,,] [reactor-http-nio-2] d.DemoController: This log is missing baggage information!!!
[INFO] [2023-08-14 17:47:20,856] [demo-service,999927cdec71191383f28bee90fc0658,208337a4de174dbf,,] [reactor-http-nio-2] d.DemoController: This log is missing baggage information!!!
```
