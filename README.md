# bazel-localhost-resolving

running on mac, no sandboxing - all green
running on mac with sandboxing - only the anyLocalAddress passes. the 2 other fail on 'java.net.BindException: Operation not permitted'
running on docker-on-mac, no sandboxing - all green
running on docker-on-mac, with sandboxing - anyLocalAddress passes, shouldBindTolocalhost also passes. bindToLocalHost still fails.

so, "localhost" fails to bind on mac, even though it should.

.Address - localhost/127.0.0.1:0
.Address - 15482f9788df/172.17.0.4:0
E.Address - /0.0.0.0:0

