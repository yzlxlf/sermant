/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huaweicloud.sermant.backend.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 服务可见性配置
 *
 * @since 2022-12-07
 * @author zhp
 */
@Component
@Configuration
public class VisibilityConfig {
    @Value("${visibility.effectiveTimes}")
    private long effectiveTimes;

    public long getEffectiveTimes() {
        return effectiveTimes;
    }

    public void setEffectiveTimes(long effectiveTimes) {
        this.effectiveTimes = effectiveTimes;
    }
}
