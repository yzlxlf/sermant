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

package com.huaweicloud.sermant.backend.service.dynamicconfig.service;

/**
 *
 * Core service for the dynamic config service.
 * This factory is used to retrieve the default configuration service.
 *
 */
public interface DynamicConfigurationFactoryService {

    /**
     * 获取动态配置接口
     *
     * @return 动态配置
     * @throws Exception 异常
     */
    DynamicConfigurationService getDynamicConfigurationService() throws Exception;

}
