package com.seckillmall.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component //加载成bean
// 当spring容器内没有tomcatEmbeddedServletContainerFactory时，会把此bean加载进spring容器
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //使用对应工厂类提供给接口，定制化TomcatConnector
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                //定制化keepalivetimeout，30秒内没有请求则自动断开连接
                protocol.setKeepAliveTimeout(30000);
                //当客户端发送超过1W个请求则自动断开keepalive连接
                protocol.setMaxKeepAliveRequests(10000);
            }
        });
    }
}
