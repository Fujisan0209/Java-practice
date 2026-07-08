# Java バックエンド開発ロードマップ

作成日: 2026-07-07
現在地: 基礎文法・OOP・コレクション・Stream API・ラムダ・Swing まで習得済み(フォルダ内コードより判断)

---

## Phase 1: Java中級の穴埋め(2〜4週間)

Spring に入る前に必須の土台。

- [ ] 例外処理の設計: 検査例外 vs 非検査例外、独自例外、try-with-resources
- [ ] ジェネリクス: `<T>`, `<? extends T>`, `<? super T>` を自分で書ける
- [ ] インターフェース活用: `Comparable`/`Comparator`、関数型インターフェース(`Function`, `Predicate`, `Supplier`, `Consumer`)
- [ ] コレクション使い分け: `Map`/`Set` を含む。`HashMap` の仕組み(hashCode/equals)
- [ ] Stream 発展: `collect(Collectors.groupingBy/toMap)`、`flatMap`、`reduce`
- [ ] record / enum / sealed など モダンJava機能(Java 17+)
- [ ] ファイルI/O: `Files`, `Path`, CSV/JSON の読み書き
- [ ] マルチスレッド入門: `Thread`, `ExecutorService`, `CompletableFuture`(深入り不要、概念理解)

**練習課題**: CSVから読んだデータを groupingBy で集計してJSONで出力するCLIツール

## Phase 2: 開発ツールとエコシステム(1〜2週間)

- [ ] Maven または Gradle: 依存管理、ビルド、`pom.xml`/`build.gradle` が読める
- [ ] IntelliJ IDEA: デバッガ(ブレークポイント、ステップ実行)、リファクタリング機能
- [ ] JUnit 5 + AssertJ: 単体テストを書く習慣をつける(Springの前に必須)
- [ ] Git 発展: branch, merge, pull request の流れ(GitHub Flow)

**練習課題**: Phase 1 の課題を Gradle プロジェクト化し、JUnit テストを付ける

## Phase 3: Web の基礎知識(1〜2週間)

- [ ] HTTP: メソッド(GET/POST/PUT/DELETE)、ステータスコード、ヘッダー、Cookie
- [ ] REST API の設計原則: リソース指向、JSON レスポンス
- [ ] SQL: SELECT/JOIN/GROUP BY/INSERT/UPDATE。PostgreSQL か MySQL を手元で動かす
- [ ] 簡単な DB 設計: 正規化、主キー・外部キー

**練習課題**: 書籍管理のテーブル設計をして SQL で CRUD 操作(Book クラスの延長で)

## Phase 4: Spring Boot(2〜3ヶ月)

ここが本丸。順番に:

1. [ ] Spring Boot で Hello World → `@RestController` で JSON を返す
2. [ ] DI(依存性注入)と `@Service`/`@Repository`/`@Component` の理解
3. [ ] Spring Data JPA: エンティティ、リポジトリ、DB 連携
4. [ ] バリデーション(`@Valid`)と例外ハンドリング(`@ControllerAdvice`)
5. [ ] Spring Security 入門: ログイン認証、JWT
6. [ ] テスト: `@SpringBootTest`, MockMvc, Mockito

**練習課題**: 書籍管理 REST API(CRUD + 検索 + 認証)を一本作り切る → GitHub 公開

## Phase 5: 実務レベルへ(継続)

- [ ] Docker: アプリと DB をコンテナで動かす
- [ ] デプロイ: Render / Railway / AWS などに公開
- [ ] ログ(SLF4J)、設定管理(profiles)、環境変数
- [ ] OpenAPI (Swagger) でAPI ドキュメント
- [ ] ポートフォリオ: オリジナルの API + フロント(簡単でよい)を1本

---

## 周辺で覚えたほうがいいこと(優先度順)

| 優先度 | 項目 | 理由 |
|--------|------|------|
| ★★★ | SQL | バックエンドの半分は DB 操作。Java より先に詰まる |
| ★★★ | Git / GitHub | チーム開発の前提。PR ベースの流れまで |
| ★★★ | HTTP / REST | API を作る仕事なので土台そのもの |
| ★★☆ | Linux / ターミナル | cd, grep, curl, ssh 程度。デプロイで必須 |
| ★★☆ | JSON / YAML | API と設定ファイルで毎日触る |
| ★★☆ | Docker | 実務ではほぼ標準 |
| ★☆☆ | JavaScript 基礎 | フロントと会話するため。深入り不要 |
| ★☆☆ | デザインパターン | Factory, Strategy, Singleton, Observer あたりだけ |
| ★☆☆ | Java Silver 資格 | 就職で評価されることがある。Phase 1 完了後なら短期で取れる |

## やらなくていいこと

- Swing の深掘り(学習教材としては良いが実務需要はほぼない)
- JSP/Servlet の詳細(Spring Boot が抽象化してくれる。概念だけ知ればOK)
- 最初から AWS 資格やマイクロサービス(Spring Boot 一本できてから)

## 目安

週10時間ペースで Phase 4 完了まで約5〜6ヶ月。ポートフォリオ完成が就職活動のスタートライン。
