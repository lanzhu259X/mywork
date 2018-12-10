package com.lanzhu.mywork.master.ribbon.rule;

import com.netflix.loadbalancer.AbstractServerPredicate;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
public class MetadataAwareRule extends DiscoveryEnableRule {

    private final DiscoveryEnabledPredicate predicate;

    /**
     * Creates new instance of {@link MetadataAwareRule}
     */
    public MetadataAwareRule() {
        this(new MetadataAwarePredicate());
    }

    public MetadataAwareRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
        super(discoveryEnabledPredicate);
        this.predicate = discoveryEnabledPredicate;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }

}
