/*
 * Copyright (c) 2017-present, wlh
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.wlh.beangenerator.beans;

/**
 *
 * Created by weilh on 2018/2/6.
 */
public class FieldInfo {

    public String type;

    public String name;

    public String describe;

    @Override
    public String toString() {
        return String.format("name : %s type : %s describe : %s", name, type, describe);
    }
}
