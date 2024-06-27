package org.ays.auth.model.request;

import org.ays.auth.model.AysRoleFilter;
import org.ays.auth.model.enums.AysRoleStatus;
import org.ays.common.model.AysPageable;
import org.ays.common.model.AysPagingBuilder;
import org.ays.common.model.AysSort;
import org.ays.common.model.TestDataBuilder;

import java.util.List;
import java.util.Set;

public class AysRoleListRequestBuilder extends TestDataBuilder<AysRoleListRequest> {

    public AysRoleListRequestBuilder() {
        super(AysRoleListRequest.class);
    }

    public AysRoleListRequestBuilder withValidValues() {

        final AysSort.AysOrder createdAtSort = AysSort.AysOrder.builder()
                .property("createdAt")
                .direction(AysSort.Direction.DESC)
                .build();

        return this
                .withPagination(new AysPagingBuilder().withValidValues().build())
                .withOrders(List.of(createdAtSort))
                .initializeFilter();
    }

    public AysRoleListRequestBuilder withPagination(AysPageable aysPageable) {
        data.setPageable(aysPageable);
        return this;
    }

    public AysRoleListRequestBuilder withOrders(List<AysSort.AysOrder> orders) {
        data.getPageable().setOrders(orders);
        return this;
    }

    public AysRoleListRequestBuilder withoutOrders() {
        data.getPageable().setOrders(null);
        return this;
    }

    public AysRoleListRequestBuilder withoutFilter() {
        data.setFilter(null);
        return this;
    }

    private AysRoleListRequestBuilder initializeFilter() {
        data.setFilter(AysRoleFilter.builder().build());
        return this;
    }

    public AysRoleListRequestBuilder withFilter(AysRoleFilter filter) {
        data.setFilter(filter);
        return this;
    }

    public AysRoleListRequestBuilder withName(String name) {
        data.getFilter().setName(name);
        return this;
    }

    public AysRoleListRequestBuilder withStatuses(Set<AysRoleStatus> statuses) {
        data.getFilter().setStatuses(statuses);
        return this;
    }


}
