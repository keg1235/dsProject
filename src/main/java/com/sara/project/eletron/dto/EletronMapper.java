package com.sara.project.eletron.dto;

import java.math.BigDecimal;

public interface EletronMapper {

    public String  getIN_DATE();
    public BigDecimal getNOW_TOTAL();
    public Integer getMAX_VALUE();
    public Integer getMIN_VALUE();
    public String getIP_ADDRESS();
    public String getMIN_DATE();
    public String getMAX_DATE();

}
