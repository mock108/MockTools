# MockTools

MockTools is a monorepo for internal tooling and platform-level services, primarily developed in Java (Spring Boot). It contains multiple modules designed to support authentication, core service orchestration, and encryption infrastructure.

---

## 🧩 Modules

### `Polaris`

Authentication & Authorization Service

- Handles login, JWT issuance (currently delegating JWT logic to Algol)
- Planned: Session/refresh token management, policy-based access

### `Zenith`

Core Application Platform

- Provides core platform logic and user-facing endpoints
- Relies on JWT verification using Algol

### `Algol`

Encryption and Token Utilities

- AES-based encryption/decryption (`AES/CBC/PKCS5Padding`)
- RSA-based JWT issuance/verification (RS256)
- Modular builder-based design (easy DI with Spring)
- No external exposure via Maven Central (internal only)

### `AlgolTool`

CLI Utility for encryption and key generation

- Depends on `Algol` module directly
- Used for:
  - AES/RSA key generation
  - File encryption/decryption
  - JWT testing/verification
- Packaged via GitHub Pages, not GitHub Releases

---

## 🚀 Release Strategy

### GitHub Releases

Used **only for service artifacts** (e.g., WAR files for Polaris/Zenith).

- Triggers CI/CD for deployment to internal environments
- Example: `v1.2.0` → WARs + changelog published under `/releases`

### GitHub Pages

Used for **internal CLI tool downloads** such as `AlgolTool`.

- CLI artifacts (`.jar`, `.exe`, `.zip`) are placed in:
  - `https://mock108.github.io/MockTools/algol-tool/v1.2.0/`
- Versioned and expandable (e.g., `/validator/`, `/configgen/` in future)

---

## 🛠 Local Development

This repository is built as a Maven multi-module project.

```
MockTools/
├── MockToolsParent/   # Parent POM
├── Algol/             # Crypto + JWT utility
├── Polaris/           # Auth microservice
├── Zenith/            # Core service
├── AlgolTool/         # CLI utility (depends on Algol)
```

### Build All

```bash
mvn clean install
```

### Package AlgolTool only

```bash
mvn clean package -pl AlgolTool -am
```

---

## 📦 Planned Extensions

- GUI wrapper for AlgolTool (Electron-based, cross-platform)
- CLI argument parser improvements (Picocli or JCommander)
- Internal S3-like deployment automation via Actions

---

## 📘 License

Internal use only – licensing TBD

