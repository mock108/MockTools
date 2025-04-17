# MockTools

MockTools is a monorepo for internal tools and platform-level services, primarily developed in Java (Spring Boot). It contains multiple modules that support authentication, service orchestration, and encryption infrastructure.

---

## 🧩 Modules

### `Polaris`
Authentication & Authorization Service
- Handles login and JWT issuance (delegates cryptographic logic to Algol)
- Future: Session/refresh token handling, policy-based access control

### `Zenith`
Core Application Platform
- Provides core business logic and public-facing endpoints
- Uses Algol for JWT verification

### `Algol`
Cryptography and JWT Utilities
- AES encryption/decryption (`AES/CBC/PKCS5Padding`)
- RSA JWT issuance and verification (RS256)
- Claim parsing and role extraction
- Modular builder-based design (Spring DI ready)
- Not published to Maven Central; internal use only

### `AlgolTool`
CLI utility for encryption and key management
- Depends on `Algol`
- Use cases:
  - AES/RSA key generation
  - File encryption/decryption
  - JWT issuance and verification tests
- Packaged via GitHub Pages (not as a release asset)

---

## 🚀 Release Strategy

### GitHub Releases
Used **only for full service artifacts**, such as WAR files for Polaris and Zenith.
- Tagged release versions (e.g., `v1.2.0`) include WARs and changelogs
- Triggers deployment automation to internal environments

### GitHub Pages
Used to host internal **CLI tool builds and binaries**
- Files are available under:
  - `https://mock108.github.io/MockTools/algol-tool/v1.2.0/`
- Supports versioning and flexible layout (e.g., `/validator/`, `/configgen/` planned)

---

## 🛠 Local Development

This repository is structured as a Maven multi-module project:

```
MockTools/
├── MockToolsParent/   # Maven root project (parent POM)
├── Algol/             # Cryptographic library
├── Polaris/           # Auth service
├── Zenith/            # Core business service
├── AlgolTool/         # CLI utility (uses Algol)
```

### Build all modules
```bash
mvn clean install
```

### Build only AlgolTool and its dependencies
```bash
mvn clean package -pl AlgolTool -am
```

---

## 🔗 Tool Downloads (GitHub Pages)

- Example: [algol-tool v1.2.0](https://mock108.github.io/MockTools/algol-tool/v1.2.0/)
- Available formats: `.jar`, `.exe`, `.zip`
- Intended for internal use only

---

## 📦 Planned Features

- Cross-platform GUI wrapper for AlgolTool (Electron-based)
- Improve CLI argument parsing (Picocli or JCommander)
- Internal S3-compatible upload via GitHub Actions
- Signature and checksum generation for binaries

---

## 📘 License
For internal use only. License terms TBD.

---

## 📓 Naming Notes

- **Polaris**: Points the way (auth anchor)
- **Zenith**: The peak of application flow
- **Algol**: A variable star, chosen for its duality (encrypt/decrypt)

These names reflect a constellation-based naming convention used across internal platform components.
