# AlgolTool

AlgolTool は、AES暗号化とRSA署名付きJWTの生成／検証に対応した CLI ツールです。  
秘密鍵・公開鍵・AESキー・IVの生成と、暗号化／復号処理をコマンドラインから簡単に行えます。

## 特長

- AES（CBC/PKCS5Padding）による暗号化・復号
- RSA（2048bit）による JWT 署名・検証（JJWT利用）
- Spring Boot + Picocli による CLI 実装
- 鍵情報の出力先は `--secret-dir` にて指定可能（デフォルトは `~/.algol/secret`）
- 実行ログは標準出力とファイル出力に分離し、用途別に最適化

## 前提

- Java 21
- GraalVM（ネイティブビルドする場合）

## インストール

### GitHub Pages 経由ダウンロード

https://mock108.github.io/MockTools/algol-tool/v1.2.0/

- `algol-tool.jar`（通常のJava実行用）
- `algol-tool.exe`（Windowsネイティブ実行用）

## コマンド一覧

```bash
algoltool --help
```

```
Usage: algoltool [-hV] [--secret-dir=<secretDir>] [COMMAND]
CLI tool for encryption and key management using Algol.
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
      --secret-dir=<secretDir>
                  鍵ファイル格納ディレクトリ
Commands:
  init     初期鍵ファイル（RSA鍵、AES鍵、IV）を ~/.algol/secret/ に生成します
  encrypt  指定文字列をAESで暗号化してBase64出力します
  decrypt  指定文字列をAESで復号します（Base64入力）
```

## 使用例

```bash
# 鍵を生成（~/.algol/secret に作成）
algoltool init

# AES暗号化（Base64で出力）
algoltool encrypt -t "Hello, World!"

# AES復号
algoltool decrypt -t "（上で出力されたBase64文字列）"

# 任意ディレクトリに鍵保存する場合
algoltool --secret-dir=c:/my/secret init
```

## application.yml の設定

```yaml
spring:
  application.name: AlgolTool

algol:
  secret-dir: ${user.home}/.algol/secret  # 鍵ファイル格納先（環境変数で上書き可能）
```

## ログ設定（logback.xml）

- `STDOUT`：INFO以上のログを表示（主にコマンド実行結果）
- `./algol-tool-log/algol-tool-YYYY-MM-DD.log`：DEBUG以上の詳細ログを保存

出力先などの詳細は [`src/main/resources/logback.xml`](src/main/resources/logback.xml) を参照してください。
