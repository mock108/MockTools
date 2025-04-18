## 📘 使用方法 (Usage)

### 🔧 初期化 (鍵ファイル生成)

```bash
java -jar algoltool.jar init
```

デフォルトで `~/.algol/secret/` に以下のファイルが生成されます:

- `secret.key`：AES鍵
- `secret.iv`：AES IV
- `private.pem`：RSA秘密鍵
- `public.pem`：RSA公開鍵

▶ `--secret-dir` で保存先を変更可能:

```bash
java -jar algoltool.jar --secret-dir ./mykeys init
```

---

### 🔐 文字列を暗号化 (AES)

```bash
java -jar algoltool.jar encrypt --text "平文文字列"
```

- 出力：Base64形式の暗号化文字列
- 鍵保存元は `--secret-dir` で指定可能

```bash
java -jar algoltool.jar --secret-dir ./mykeys encrypt --text "Hello"
```

---

### 🔓 文字列を復反 (AES)

```bash
java -jar algoltool.jar decrypt --text "暗号化されたBase64文字列"
```

- 出力：復反された平文

---

## 🛠 オプション一覧

| オプション名 | 説明 | 備考 |
|--------------|------|------|
| `--secret-dir` | 鍵ファイルの保存/読み込みディレクトリ | デフォルトは `~/.algol/secret` |
| `--text` | 暗号化/復反対象の文字列 | `encrypt` / `decrypt` 共通 |

---

## 📂 鍵ファイル構成

```bash
~/.algol/secret/
├── secret.key     ← AES鍵
├── secret.iv      ← AES初期化ベクトル
├── private.pem    ← RSA秘密鍵
└── public.pem     ← RSA公開鍵
```

---

## 🚀 ビルド方法 (開発用)

```bash
mvn clean package
```

- 出力：`target/algoltool.jar`
- Native Image対応の場合はこの JAR を元にバイナリ化

