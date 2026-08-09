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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link CxfClientUtils}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@DisplayName("CxfClientUtils")
class CxfClientUtils_Test {

    /**
     * Testable subclass that overrides createClient to return a mock.
     */
    private static class TestableClientFactory extends JaxWsDynamicClientFactory {
        private final Client mockClient;

        TestableClientFactory(Client mockClient) {
            super(null);
            this.mockClient = mockClient;
        }

        @Override
        public Client createClient(String wsdlUrl) {
            return mockClient;
        }
    }

    @Test
    @DisplayName("invoke calls client.invoke and returns result")
    void invoke_callsClientInvoke() throws Exception {
        Client mockClient = mock(Client.class);
        Object[] expectedResult = new Object[]{"hello", "world"};
        when(mockClient.invoke("sayHello", "param1")).thenReturn(expectedResult);

        JaxWsDynamicClientFactory originalDcf = CxfClientUtils.dcf;
        try {
            CxfClientUtils.dcf = new TestableClientFactory(mockClient);

            Object[] result = CxfClientUtils.invoke("http://example.com?wsdl", "sayHello", "param1");

            assertThat(result).isEqualTo(expectedResult);
            verify(mockClient).invoke("sayHello", "param1");
        } finally {
            CxfClientUtils.dcf = originalDcf;
        }
    }

    @Test
    @DisplayName("invoke propagates exception from client creation")
    void invoke_propagatesException() throws Exception {
        JaxWsDynamicClientFactory originalDcf = CxfClientUtils.dcf;
        try {
            CxfClientUtils.dcf = new JaxWsDynamicClientFactory(null) {
                @Override
                public Client createClient(String wsdlUrl) {
                    throw new RuntimeException("connection failed");
                }
            };

            assertThatThrownBy(() ->
                CxfClientUtils.invoke("http://bad-url?wsdl", "method")
            ).isInstanceOf(Exception.class);
        } finally {
            CxfClientUtils.dcf = originalDcf;
        }
    }
}
