package com.yonghui.miniPocket.bean;

import java.io.Serializable;

/**
 * Created by Yonghui Rao
 */
public class Version implements Serializable{
    public int versioncode=0;

    public Version(int versioncode) {
        this.versioncode = versioncode;
    }
}
