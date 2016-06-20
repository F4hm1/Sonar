package com.lambdaworks.redis.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * Predefined DNS resolvers.
 * 
 * @author Mark Paluch
 * @since 4.2
 */
public enum DnsResolvers implements DnsResolver {

    /**
     * Java VM default resolver.
     */
    JVM_DEFAULT;

    @Override
    public InetAddress[] resolve(String host) throws UnknownHostException {
        return InetAddress.getAllByName(host);
    }
}
