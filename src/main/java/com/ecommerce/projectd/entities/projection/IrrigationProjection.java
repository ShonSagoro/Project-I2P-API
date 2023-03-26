package com.ecommerce.projectd.entities.projection;

public interface IrrigationProjection {

    Long getId();

    Integer getPrevious_Moisture_State();

    Integer getMoisture_State();

    String getDate();

    Long getIrrigation_System_Id();
}
