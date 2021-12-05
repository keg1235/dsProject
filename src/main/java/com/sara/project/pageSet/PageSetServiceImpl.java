package com.sara.project.pageSet;

import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.EletronService;
import com.sara.project.pageSet.dto.PageSetRequest;
import com.sara.project.pageSet.dto.PageSetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageSetServiceImpl implements PageSetService {

    @Autowired
    private PageSetRepository pageSetRepository;

    @Override
    public PageSetResponse get() {
        PageSet pageSet =  pageSetRepository.findById(1l).orElse(null);
        PageSetResponse pageSetResponse = PageSetResponse.of(pageSet);
        return pageSetResponse;
    }

    @Override
    public void save(PageSetRequest pageSetRequest) {
        PageSet pageSet = pageSetRequest.of();
        pageSetRepository.save(pageSet);
    }
}
