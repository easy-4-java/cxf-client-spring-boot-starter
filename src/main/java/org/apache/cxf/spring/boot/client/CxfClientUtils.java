/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class CxfClientUtils {

	private static JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	
	/**
	 * 
	 * @description	： 动态调用webService接口
	 * @author <a href="https://github.com/loong10k">Loong Wan</a>
	 * @date 		：2017年11月13日 下午5:20:28
	 * @param wsdlUrl 地址(带 ?wsdl)
	 * @param optName 调用的操作名称(一般为方法名称)
	 * @param pars 调用时要传的参数值，顺序要和接口对应
	 * @return
	 * @throws Exception
	 */
	public static Object[] invoke(String wsdlUrl,String optName, Object... pars) throws Exception {
		Client client = dcf.createClient(wsdlUrl);
		return client.invoke(optName, pars);
	}
	 
}
