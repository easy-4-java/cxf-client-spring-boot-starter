/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.cxf.spring.boot.client;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

public class CxfClientUtils_Test {
	
	@Test
	public void testname1() throws Exception {

        //sayHello 为接口中定义的方法名称   张三为传递的参数   返回一个Object数组
	    Object[] objects = CxfClientUtils.invoke("http://127.0.0.1:9000/hello?wsdl", "sayHello", "张三");   
	    //输出调用结果
	    System.out.println(objects[0].toString()); 
	    
	}
	
	@Test
	public void testname2() throws Exception {

		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	    org.apache.cxf.endpoint.Client client = dcf.createClient("http://127.0.0.1:9000/hello?wsdl");
        //sayHello 为接口中定义的方法名称   张三为传递的参数   返回一个Object数组
	    Object[] objects= client.invoke("sayHello", "张三");   
	    //输出调用结果
	    System.out.println(objects[0].toString()); 
	    
	}
	
	@Test
	public void testname3() throws Exception {

	    
	    /*
	    JaxWsProxyFactoryBean  factoryBean=new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(IHelloWorldService.class);
        factoryBean.setAddress("http://localhost:9000/hello");
        IHelloWorldService impl=(IHelloWorldService) factoryBean.create();
        System.out.println(impl.sayHello("张三")); */
	}

}
