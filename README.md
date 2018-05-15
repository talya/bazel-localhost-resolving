# bazel-localhost-resolving

- when no sandboxing all tests are green.

- running on mac with sandboxing
  - bindToLocalAddress - uses InetAddress.getLocalHost - fails on 'java.net.BindException: Operation not permitted'
    (resolves to something like '15482f9788e1/172.17.0.4:0')
    
  - bind to "localhost" - **fails on the same 'java.net.BindException: Operation not permitted'**
    (resolves to 'localhost/127.0.0.1:0')
    
- running on docker-on-mac, with sandboxing
  - bindToLocalAddress - uses InetAddress.getLocalHost - same failure
  
  - bind to "localhost" - **passes**

so, "localhost" fails to bind on mac, even though it should.
seems related to the code from the [DarwinSandboxedSpawnRunner](https://github.com/bazelbuild/bazel/blob/2e4f703d361823fa12df9ddb57f21189743b2c74/src/main/java/com/google/devtools/build/lib/sandbox/DarwinSandboxedSpawnRunner.java#L312)
```
if (!allowNetwork) {
        out.println("(deny network*)");
        out.println("(allow network* (local ip \"localhost:*\"))");
        out.println("(allow network* (remote ip \"localhost:*\"))");
        out.println("(allow network* (remote unix-socket))");
      }
```
