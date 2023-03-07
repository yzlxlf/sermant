/*
 * Copyright (C) 2021-2021 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.sermant.backend.service.dynamicconfig.nop;

import com.huaweicloud.sermant.backend.service.dynamicconfig.Config;
import com.huaweicloud.sermant.backend.service.dynamicconfig.service.ConfigurationListener;
import com.huaweicloud.sermant.backend.service.dynamicconfig.service.DynamicConfigurationService;

/**
 *   This class is for testing purpose only.
 *
 * @deprecated 测试类
 */
@Deprecated
public class NopDynamicConfigurationService implements DynamicConfigurationService {
    static private NopDynamicConfigurationService serviceInst;

    private NopDynamicConfigurationService() {
        // no-op
    }

    /**
     * 获取nop动态配置服务
     *
     * @return nop动态配置服务
     */
    public static synchronized NopDynamicConfigurationService getInstance() {
        if (serviceInst == null) {
            serviceInst = new NopDynamicConfigurationService();
        }
        return serviceInst;
    }


    @Override
    public boolean addConfigListener(String key, String group, ConfigurationListener listener) {
        return true;
    }

    @Override
    public boolean removeConfigListener(String key, String group, ConfigurationListener listener) {
        return true;
    }

    @Override
    public String getConfig(String key, String group) throws IllegalStateException {
        // no-op
        return "";
    }

    /**
     * @since 2.7.5
     */
    @Override
    public boolean publishConfig(String key, String group, String content) {
        return true;
    }

    @Override
    public String getDefaultGroup() {
        return Config.getDefaultGroup();
    }

    @Override
    public long getDefaultTimeout() {
        return Config.getTimeout_value();
    }

    @Override
    public void close() throws Exception {
        // no-op
    }

}
