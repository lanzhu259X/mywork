package com.lanzhu.mywork.master.ribbon.rule;

import com.lanzhu.mywork.master.ribbon.IRibbonFilterContext;
import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * description:
 *  A default implementation of {@link DiscoveryEnabledServer} that matches the instance against the
 *  attributes registered through
 * @author lanzhu259X
 * @date 2018-09-22
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {

    @Override
    protected boolean apply(DiscoveryEnabledServer server) {
        final IRibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
        final Set<Map.Entry<String, String>> attributes =
                Collections.unmodifiableSet(context.getAttributes().entrySet());
        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
        return metadata.entrySet().containsAll(attributes);
    }
}
