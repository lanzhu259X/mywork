//package com.lanzhu.mywork.master.common.ribbon;
//
//import com.netflix.loadbalancer.AbstractServerPredicate;
//import org.springframework.util.Assert;
//
///**
// * description:
// * A metadata aware {@link DiscoveryEnabledRule} implementation.
// *
// * @see DiscoveryEnabledRule
// * @see MetadataAwarePredicate
// *
// * @author lanzhu259X
// * @date 2018-12-25
// */
//public class MetadataAwareRule extends DiscoveryEnabledRule {
//
//    private final DiscoveryEnabledPredicate predicate;
//
//    /**
//     * Creates new instance of {@link DiscoveryEnabledRule} class with specific predicate.
//     *
//     * @param discoveryEnabledPredicate the discovery enabled predicate, can't be null
//     * @throws IllegalArgumentException if {@code discoveryEnabledPredicate} is {@code null}
//     */
//    public MetadataAwareRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
//        super(discoveryEnabledPredicate);
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
//
//
//    /**
//     * Creates new instance of {@link MetadataAwareRule}.
//     */
//    public MetadataAwareRule() {
//        this(new MetadataAwarePredicate());
//    }
//}
