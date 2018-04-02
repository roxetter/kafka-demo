# kafka-demo

一个简单的kafka读写示例，包括两个独立的可运行程序，一个是从控制台读取输入写入kafka的produder，另一个是从kafka读入并打印的consumer。

## 用法
### 用mvn编译并打包
mvn clean compile assembly:single

### 运行producer
java -cp kafka-demo-{version}.jar cn.alfredcheung.demo.kafka.Producer

### 运行consumer
java -cp kafka-demo-{version}.jar cn.alfredcheung.demo.kafka.Consumer
