/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huaweicloud.sermant.core.service.visibility.config;

import com.huaweicloud.sermant.core.config.common.BaseConfig;
import com.huaweicloud.sermant.core.config.common.ConfigTypeKey;

/**
 * 配置信息
 *
 * @author zhp
 * @since 2022-12-05
 */
@ConfigTypeKey("visibility.service")
public class VisibilityServiceConfig implements BaseConfig {

    private boolean enableStart;

    public boolean isEnableStart() {
        return enableStart;
    }

    public void setEnableStart(boolean enableStart) {
        this.enableStart = enableStart;
    }
}
