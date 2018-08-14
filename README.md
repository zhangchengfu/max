1，4个项目：
	eureka-server:服务注册中心
	service-a,service-b:服务集群（内部服务）
	api-gateway:服务网关
2,分别启动：eureka-server,service-a,service-b,api-gateway
3,服务注册中心url:localhost:1111
  service-a api url:localhost:2222/add?a=1&b=2
  service-b api url:localhost:3333/add?a=1&b=2
  通过网关serviceid映射访问service-a的add服务:localhost:5555/api-a/add?a=1&b=2
  通过网关serviceid映射访问service-b的add服务:localhost:5555/api-b/add?a=1&b=2
  通过网关url映射访问service-a的add服务:localhost:5555/api-a-url/add?a=1&b=2
4,参考链接：
  http://blog.csdn.net/u013322876/article/details/61416196
  http://blog.didispace.com/springcloud5/