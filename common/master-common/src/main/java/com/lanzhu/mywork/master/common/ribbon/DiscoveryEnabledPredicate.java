package com.lanzhu.mywork.master.common.ribbon;

import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-25
 */
@Log4j2
public abstract class DiscoveryEnabledPredicate extends AbstractServerPredicate {
    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        return predicateKey != null && predicateKey.getServer() instanceof DiscoveryEnabledServer
                && apply((DiscoveryEnabledServer) predicateKey.getServer());
    }

    /**
     * Returns whether the specific {@link DiscoveryEnabledServer} matches this predicate.
     *
     * @param server the discovered server
     * @return whether the server matches the predicate
     */
    protected abstract boolean apply(DiscoveryEnabledServer server);

    @Override
    public List<Server> getEligibleServers(List<Server> servers, Object loadBalancerKey) {
        List<Server> result = super.getEligibleServers(servers, loadBalancerKey);
        log.debug("envflag={} find server={} after filter={}",
                RibbonFilterContextHolder.getCurrentContext().getEnvFlag(), getServerIps(servers),
                getServerIps(result));
        if (CollectionUtils.isEmpty(result)) {
            result = servers;
        }
        return result;
    }

    private String getServerIps(List<Server> servers) {
        if (servers != null) {
            StringBuilder builder = new StringBuilder();
            for (Server server : servers) {
                builder.append(server.getHost())
                        .append(":")
                        .append(server.getPort())
                        .append(",");
            }
            return builder.toString();
        }
        return null;
    }

}
