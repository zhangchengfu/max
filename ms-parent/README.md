### 基于Spring Cloud的微服务项目架构,Eureka作为注册中心，Gateway作为网关

### 启动顺序RegistryApplication -> 网关 GatewayApplication -> DinersApplication

刷新token
http://localhost:8082/oauth/token?grant_type=refresh_token&refresh_token=a78641e5-f86d-49ad-8369-cdb28cfbac57

检查token
http://localhost:8082/oauth/check_token?token=a78641e5-f86d-49ad-8369-cdb28cfbac57




