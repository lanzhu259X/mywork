package com.lanzhu.mywork.master.ribbon.rule;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateBasedRule;
import org.springframework.util.Assert;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
public class DiscoveryEnableRule extends PredicateBasedRule {

    private final AbstractServerPredicate predicate;

    public DiscoveryEnableRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
        Assert.notNull(discoveryEnabledPredicate, "discoveryEnabledPredicate can't be null");
        this.predicate = discoveryEnabledPredicate;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }
}
