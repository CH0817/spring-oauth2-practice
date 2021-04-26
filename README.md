##  介紹

- simple-oauth2-authorization-server: 簡單實現 OAuth2
- memory-client-oauth2-authorization-server: 記憶體保存 token 範例
- jdbc-client-token-oauth2-authorization-server: JDBC 保存 token 範例
- redis-token-oauth2-authorization-server: Redis 保存 token 範例
- jwt-client-token-oauth2-authorization-server: JWT 驗證範例
- spring-oauth2-resource-server: 資源伺服器
- spring-oauth2-dependencies: 統一專案依賴

## 授權碼模式取 token

1. 發出鑑權請求(需使用 browser)，http://localhost:8880/oauth/authorize?response_type=code&client_id=oauth&redirect_uri=https://www.google.com&scope=all (get)
2. 取得 token ， http://localhost:8880/oauth/token (post)，參數如下
   1. grant_type = authorization_code
   2. redirect_uri = 同鑑權請求
   3. client_id = 伺服器定義
   4. code = 鑑權請求回覆
   5. scope = 同鑑權請求
   6. 須在 header 攜帶 client id 與 client secure

## 密碼模式取 token

取得 token ， http://localhost:8880/oauth/token (post)，參數如下

1. grant_type = password
2. username = 帳號
3. password = 密碼
4. scope = 任意
5. 須在 header 攜帶 client id 與 client secure
    
## 請求資源

http://localhost:8888/resource/test (get)，須在 header 攜帶 bearer token

## 檢查 token

http://localhost:8880/oauth/check_token (post)，參數如下

1. token = 要檢查的 token
2. 須在 header 攜帶 client id 與 client secure

## 刷新 token

http://localhost:8880/oauth/token (post)，參數如下

1. grant_type = refresh_token
2. refresh_token = 請求 token 時給的 refresh token
3. 須在 header 攜帶 client id 與 client secure

## 參考

- [Spring Security Oauth2 从零到一完整实践（一）](https://echocow.cn/articles/2019/07/14/1563082088646.html)
- [Spring Security Oauth2 从零到一完整实践（二）自动配置实现](https://echocow.cn/articles/2019/07/14/1563082247386.html)
- [Spring Security Oauth2 从零到一完整实践（三）授权服务器配置](https://echocow.cn/articles/2019/07/14/1563096109753.html)
- [Spring Security Oauth2 从零到一完整实践（四）资源服务器配置](https://echocow.cn/articles/2019/07/20/1563611848587.html)
- [Spring Security Oauth2 从零到一完整实践（五）自定义授权模式（手机、邮箱等）](https://echocow.cn/articles/2019/07/30/1564498598952.html)
- [Spring Security Oauth2 从零到一完整实践（六）踩坑记录](https://echocow.cn/articles/2020/01/20/1579503807596.html)  