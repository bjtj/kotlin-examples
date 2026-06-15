# TLS example

<https://square.github.io/okhttp/features/https/>

## API Doc

<https://square.github.io/okhttp/5.x/okhttp-tls/index.html>

## Testing certificate pinning w/ self-signed cert

1. copy `local.default.properties` to `local.properties`

2. set value of `pinnedpubkey`

e.g.)

``` ini
pinnedpubkey=sha256/Gyy15P...
```
