package com.i0dev.StaffPro.config;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SPlayerStorage extends AbstractConfiguration {


    Set<SPlayer> storage = new HashSet<>();


    public SPlayerStorage(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }
}
