# liflig-properties

Library for fetching properties from AWS Parameter Store and Secrets Manager, and type- and null-safe extension methods to `java.util.Properties` for Kotlin.

The path from which properties are loaded is specified in the environment variale `SSM_PREFIX`.
Properties are loaded from `SSM_PREFIX/config/` and secrets are loaded from secret names specified in parameters with prefix `SSM_PREFIX/secrets/`.

`liflig-cdk` has built-in support for creating secrets and properties that `liflig-properties` can load.

This library is currently only distributed in Liflig
internal repositories.

## Contributing

This project follows
https://confluence.capraconsulting.no/x/fckBC

To check build before pushing:

```bash
mvn verify
```

The CI server will automatically release new version for builds on master.
