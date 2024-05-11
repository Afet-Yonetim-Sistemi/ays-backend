package org.ays.admin_user.model.dto.request;

import org.ays.common.model.AysPaging;
import org.ays.common.model.AysPagingBuilder;
import org.ays.common.model.AysSorting;
import org.ays.common.model.TestDataBuilder;

import java.util.List;

@Deprecated(since = "AdminUserListRequestBuilder V2 Production'a alınınca burası silinecektir.", forRemoval = true)
public class AdminUserListRequestBuilder extends TestDataBuilder<AdminUserListRequest> {

    public AdminUserListRequestBuilder() {
        super(AdminUserListRequest.class);
    }

    public AdminUserListRequestBuilder withValidValues() {
        return this
                .withPagination(new AysPagingBuilder().withValidValues().build())
                .withSort(null);
    }

    public AdminUserListRequestBuilder withPagination(AysPaging aysPaging) {
        data.setPagination(aysPaging);
        return this;
    }

    public AdminUserListRequestBuilder withSort(List<AysSorting> sorting) {
        data.setSort(sorting);
        return this;
    }
}
