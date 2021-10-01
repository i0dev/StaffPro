package com.i0dev.StaffPro.templates;

import com.i0dev.StaffPro.Heart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractConfiguration {

    public transient Heart heart = null;
    public transient String path = "";

}
