# bazel-localhost-resolving

- sample repo for bazel issue - [can't bind to "localhost" with sandboxing on OS X](https://github.com/bazelbuild/bazel/issues/5206)

- when no sandboxing all tests are green.

- running on linux with sandboxing - all green.

- running on mac with sandboxing
  - bindToLocalAddress - uses InetAddress.getLocalHost - fails on 'java.net.BindException: Operation not permitted'
    
  - bind to "localhost" - **fails on the same 'java.net.BindException: Operation not permitted'**
    (resolves to **'localhost/127.0.0.1:0'**)
    
- running on docker (on mac and on linux), with sandboxing
  - bindToLocalAddress - uses InetAddress.getLocalHost - same failure
(resolves to something like '15482f9788e1/172.17.0.4:0')
  
  - bind to "localhost" - **passes**

so, "localhost/127.0.0.1:0" fails to bind in sandbox mode on mac, even though it should.
seems related to the code from the [DarwinSandboxedSpawnRunner](https://github.com/bazelbuild/bazel/blob/2e4f703d361823fa12df9ddb57f21189743b2c74/src/main/java/com/google/devtools/build/lib/sandbox/DarwinSandboxedSpawnRunner.java#L312)
```
if (!allowNetwork) {
        out.println("(deny network*)");
        out.println("(allow network* (local ip \"localhost:*\"))");
        out.println("(allow network* (remote ip \"localhost:*\"))");
        out.println("(allow network* (remote unix-socket))");
      }
```

It's not possible to change this to be ``` localhost/127.0.0.1:*``` or ```localhost/*:*``` since Apple's .sb format doesn't support this (results in this error: ```host must be * or localhost in network address```)
<br>Also tried using ```out.println("(allow network* (local ip))");``` but that completely breaks the sandboxing since even external addresses are accessible. Added the ```shouldNotBindToNonLoopbackAddress``` test to gaurd against this.
