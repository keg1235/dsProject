package com.sara.project.pageSet;

import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.pageSet.dto.PageSetRequest;
import com.sara.project.pageSet.dto.PageSetResponse;

public interface PageSetService {
    PageSetResponse get();
    void save(PageSetRequest pageSetRequest);
}
