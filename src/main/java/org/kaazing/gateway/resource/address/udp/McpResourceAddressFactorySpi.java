/**
 * Copyright (c) 2007-2014 Kaazing Corporation. All rights reserved.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.kaazing.gateway.resource.address.udp;

import static java.lang.String.format;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

// TODO: remove this and detect multicast automatically in UDP acceptor / connector
public class McpResourceAddressFactorySpi extends UdpResourceAddressFactorySpi {
    
    public static final String SCHEME_NAME = "mcp";

    @Override
    public String getSchemeName() {
        return SCHEME_NAME;
    }
    
    @Override
    protected UdpResourceAddress newResourceAddress0(URI original, URI location) {
        
        UdpResourceAddress address = super.newResourceAddress0(original, location);
        URI resource = address.getResource();
        String host = resource.getHost();
        try {
            InetAddress hostAddress = InetAddress.getByName(host);
            if (!hostAddress.isMulticastAddress()) {
                throw new IllegalArgumentException(format("Expected multicast address in URI: %s", location));
            }
        }
        catch (UnknownHostException e) {
            throw new IllegalArgumentException(format("Unable to resolve DNS name: %s", host), e);
        }
        
        return address;
    }
    
}