# user-demo

一个基于 Spring Boot 的用户管理 CRUD 与分页查询练习项目，用于学习 Codex 工作流。

## 功能

- 创建、按 ID 查询、更新和逻辑删除用户。
- 分页查询用户，可按 `username` 模糊匹配、按 `status` 精确筛选。
- 使用统一的 `Result<T>` API 响应和 `PageResponse<T>` 分页数据结构。
- 使用 Flyway 管理 MySQL 表结构迁移；MyBatis-Plus 负责数据访问和逻辑删除。

项目不包含登录、鉴权、角色权限或消息队列功能。它适合本地学习，不应直接作为公开部署的生产服务。

## 技术栈

| 组件 | 项目版本或配置 |
| --- | --- |
| Java | 1.8 |
| Maven | 使用本机 Maven；仓库未提供 Maven Wrapper |
| Spring Boot | 2.7.18 |
| MyBatis-Plus | 3.5.5 |
| MySQL Connector/J | 8.0.33 |
| 数据库 | MySQL |
| 其他 | Lombok、Flyway、Bean Validation |

版本来自当前 `pom.xml`，是本项目的构建配置，不代表最新版本推荐。

## 运行前准备

1. 安装并配置 JDK 8 和 Maven。
2. 准备一个本地 MySQL 实例，并创建供本项目单独使用的数据库：

   ```sql
   CREATE DATABASE user_management
     CHARACTER SET utf8mb4
     COLLATE utf8mb4_unicode_ci;
   ```

3. 配置数据库连接。默认 URL 为 `jdbc:mysql://localhost:3306/user_management`，默认用户名为 `root`。密码从环境变量 `MYSQL_PASSWORD` 读取；未设置时为空。

   ```bash
   export MYSQL_PASSWORD='你的本地数据库密码'
   ```

   也可以在 IntelliJ IDEA 的运行配置中设置 `MYSQL_PASSWORD`。不要把真实密码提交到 Git。若需覆盖其他连接项，可使用 Spring Boot 标准环境变量，例如 `SPRING_DATASOURCE_URL`、`SPRING_DATASOURCE_USERNAME` 和 `SPRING_DATASOURCE_PASSWORD`。

请使用独立的本地演示数据库，不要把已有或重要数据的库配置给本项目。应用启动时 Flyway 会在目标库执行迁移；迁移会创建或调整 `users` 表和索引。数据库账号需要具备这些 DDL 操作所需的权限；当前迁移通过存储过程检查并调整索引，因此还需要数据库允许创建、调用和删除存储过程。只读或仅有 CRUD 权限的账号可能无法完成首次启动迁移。

## 启动

在项目根目录执行：

```bash
mvn compile
mvn spring-boot:run
```

也可以在 IntelliJ IDEA 中运行 `com.example.usermanagement.UserManagementApplication`。启动前请确认 IDE 使用 JDK 8，并已配置数据库环境变量。

打包并运行：

```bash
mvn package
java -jar target/user-management-0.0.1-SNAPSHOT.jar
```

服务默认监听 `http://localhost:8080`。

## API

所有接口前缀为 `/api/users`。请求和响应使用 JSON；分页查询参数通过 URL 传递。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/api/users` | 创建用户 |
| `GET` | `/api/users/{id}` | 按 ID 查询用户 |
| `PUT` | `/api/users/{id}` | 更新用户 |
| `DELETE` | `/api/users/{id}` | 逻辑删除用户 |
| `GET` | `/api/users` | 分页查询用户 |

### 创建用户

`username` 必填，长度最多 64；`email` 和 `phone` 可省略，长度分别最多 128 和 32。`email` 若提供则须符合邮箱格式。`status` 可选，取值为 `0` 或 `1`，省略时默认为 `1`。

```bash
curl -X POST 'http://localhost:8080/api/users' \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo-alice","email":"alice@example.test","phone":"13800000000","status":1}'
```

### 按 ID 查询

```bash
curl 'http://localhost:8080/api/users/1'
```

### 更新用户

更新接口使用 `PUT`，不是 `PATCH`。请求要求提供 `username` 和 `status`；请明确提供需要保留的其他字段。省略字段的具体更新行为请以实际数据库验证，不要假设它等同于部分更新。

```bash
curl -X PUT 'http://localhost:8080/api/users/1' \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo-alice-updated","email":"alice@example.test","phone":"13800000000","status":1}'
```

### 分页和筛选

| 参数 | 默认值 | 说明 |
| --- | --- | --- |
| `current` | `1` | 当前页，范围 1–1000 |
| `size` | `10` | 每页条数，范围 1–100 |
| `username` | 无 | 最多 64 个字符，按用户名模糊匹配 |
| `status` | 无 | 精确匹配状态，取值 `0` 或 `1` |

```bash
curl 'http://localhost:8080/api/users?current=1&size=10&username=alice&status=1'
```

### 逻辑删除

删除接口会由 MyBatis-Plus 将记录标记为已删除，不会物理移除该行。实体配置使用 `@TableLogic(value = "0", delval = "id")`，所以删除后该行的 `deleted` 值为该用户自己的 ID，不是固定的 `1`。常规查询会隐藏已删除记录。当前没有恢复已删除用户的 API。

```bash
curl -X DELETE 'http://localhost:8080/api/users/替换为创建响应中的实际ID'
```

先使用创建接口，并将响应中的实际 `id` 填入删除命令。上面的 `curl` 命令是接口示例；执行写入或删除请求前，请确认连接的是专用演示数据库。

## 响应格式

成功响应采用统一包装，`data` 根据接口不同为用户对象、分页对象或 `null`：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "demo-alice",
    "email": "alice@example.test",
    "phone": "13800000000",
    "status": 1,
    "createdAt": "2026-01-01T12:00:00",
    "updatedAt": "2026-01-01T12:00:00"
  }
}
```

这是格式示例，不是实际运行结果。分页 `data` 包含 `records`、`total`、`current`、`size` 和 `pages`。业务错误及参数校验错误也使用同一包装，例如 `code` 为 `404`、`400` 或 `500`。这些值是 JSON 响应中的业务码；当前异常处理器不设置对应的 HTTP 状态，因此客户端应检查响应体的 `code`，不要只依赖 HTTP 状态码判断业务结果。

## 数据库迁移

迁移文件位于 `src/main/resources/db/migration`，应用启动时由 Flyway 自动执行：

- `V1` 创建 `users` 表，包含用户资料、状态、逻辑删除标记和创建/更新时间，并创建 `(username, deleted)` 唯一索引。
- `V2` 为旧版表补齐 `deleted` 列，并将旧的单列用户名唯一索引调整为 `(username, deleted)` 唯一索引。结合逻辑删除时将 `deleted` 设为该行 ID 的行为，同一用户名可以在删除后再次创建。
- `V3` 添加支持未删除记录分页查询的索引。

迁移以数据库 `user_management` 已存在为前提。不要通过手工删除 Flyway 历史记录来排查迁移问题；先检查数据库连接和迁移错误信息。

## 项目结构

```text
src/main/java/com/example/usermanagement/
├── common/       # Result<T> 和分页响应
├── config/       # MyBatis-Plus 配置与自动填充
├── controller/  # HTTP 接口
├── dto/          # 请求与响应对象
├── entity/       # User 实体，对应 users 表
├── exception/    # 业务异常和统一异常处理
├── mapper/       # MyBatis-Plus Mapper
└── service/      # 用户业务接口与实现
src/main/resources/
├── application.yml
└── db/migration/ # Flyway SQL 迁移
```

## 手动检查建议

连接本地演示库并启动服务后，可以依次检查：

1. 创建用户并按 ID 查询。
2. 分别按 `username`、`status` 及组合条件分页查询。
3. 更新用户，并检查缺少必填字段或非法状态时返回的校验错误。
4. 尝试创建重复的未删除用户名，确认唯一索引约束生效。
5. 逻辑删除用户，确认 API 查询不再返回它，而数据库行仍存在且 `deleted` 已标记。

仓库当前未提供 `src/test` 测试用例；上述是手动验证步骤，不代表已执行。构建检查可运行 `mvn compile`。

## 常见启动问题

- **连接被拒绝或认证失败**：检查 MySQL 是否运行、数据库是否已创建，以及 URL、用户名和 `MYSQL_PASSWORD` 是否正确。
- **Flyway 找不到数据库**：先创建 `user_management` 数据库；Flyway 迁移只管理表结构，不创建数据库本身。
- **端口 8080 被占用**：释放该端口，或通过 `SERVER_PORT` 覆盖服务端口。
- **用户名重复**：未删除用户的 `username` 必须唯一；逻辑删除会保留记录并将 `deleted` 设为该行 ID，因此允许该用户名再次创建。
