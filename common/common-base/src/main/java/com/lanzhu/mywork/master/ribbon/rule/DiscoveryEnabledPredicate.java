package com.lanzhu.mywork.master.ribbon.rule;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Log4j2
public abstract class DiscoveryEnabledPredicate extends AbstractServerPredicate {

    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        return predicateKey != null && predicateKey.getServer() instanceof DiscoveryEnabledServer
                && apply((DiscoveryEnabledServer) predicateKey.getServer());
    }

    /**
     * 服务选择
     * @param server
     * @return
     */
    protected abstract boolean apply(DiscoveryEnabledServer server);

    @Override
    public List<Server> getEligibleServers(List<Server> servers, Object loadBalanceKey) {
        List<Server> result = super.getEligibleServers(servers, loadBalanceKey);
        if (log.isDebugEnabled()) {
            log.debug("find server={} after filter={}", getServerIps(servers), getServerIps(result));
        }
        if (CollectionUtils.isEmpty(result)) {
            result = servers;
        }
        return result;
    }

    private String getServerIps(List<Server> servers) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Server server : servers) {
            builder.append(server.getHost())
                    .append(":")
                    .append(server.getPort())
                    .append(",");
        }
        return builder.toString();
    }
}
