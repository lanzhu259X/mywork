//package com.lanzhu.mywork.master.common.ribbon;
//
//import com.lanzhu.mywork.master.ribbon.RibbonFilterContext;
//import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
//import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Set;
//
///**
// * description:
// *  A default implementation of {@link DiscoveryEnabledServer} that matches the instance against the
// *  attributes registered through
// *
// *  @see DiscoveryEnabledPredicate
// *
// * @author lanzhu259X
// * @date 2018-12-25
// */
//public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {
//
//    /**
//     * {@inheritDoc}
//     * @param server
//     * @return
//     */
//    @Override
//    protected boolean apply(DiscoveryEnabledServer server) {
//        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
//        final Set<Map.Entry<String, String>> attributes =
//                Collections.unmodifiableSet(context.getAttributes().entrySet());
//        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
//        return metadata.entrySet().containsAll(attributes);
//    }
//}
