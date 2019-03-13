//package com.lanzhu.mywork.master.common.ribbon;
//
//import com.netflix.loadbalancer.AbstractServerPredicate;
//import com.netflix.loadbalancer.PredicateBasedRule;
//import org.springframework.util.Assert;
//
///**
// * description:
// *
// * A simple {@link com.netflix.loadbalancer.IRule} for matching the discovered server instances. The actual matching is being
// * performed by the registered instance of {@link DiscoveryEnabledPredicate} allowing to adjust the
// * actual matching strategy.
// * @author lanzhu259X
// * @date 2018-12-25
// */
//public class DiscoveryEnabledRule extends PredicateBasedRule {
//
//    private final AbstractServerPredicate predicate;
//
//    /**
//     * Creates new instance of {@link DiscoveryEnabledRule} class with specific predicate.
//     *
//     * @param discoveryEnabledPredicate the discovery enabled predicate, can't be null
//     * @throws IllegalArgumentException if {@code discoveryEnabledPredicate} is {@code null}
//     */
//    public DiscoveryEnabledRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
//        Assert.notNull(discoveryEnabledPredicate, "Parameter 'discoveryEnabledPredicate' can't be null");
//        this.predicate = discoveryEnabledPredicate;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public AbstractServerPredicate getPredicate() {
//        return predicate;
//    }
//}
